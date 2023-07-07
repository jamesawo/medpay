
package com.james.medpay.features.transaction.data.repositoryImpl;

import com.james.medpay.core.annotation.RepositoryImpl;
import com.james.medpay.core.exception.InvalidRequestException;
import com.james.medpay.core.params.AmountRange;
import com.james.medpay.core.params.DateRange;
import com.james.medpay.core.params.PageParam;
import com.james.medpay.features.billing.domain.enums.BillStatus;
import com.james.medpay.features.billing.domain.usecase.IPatientBillItemUsecase;
import com.james.medpay.features.billing.domain.usecase.IPatientBillUsecase;
import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalApiUsageRepository;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalBasicDetailRepository;
import com.james.medpay.features.transaction.data.datasource.export.TransactionReceiptExporter;
import com.james.medpay.features.transaction.domain.entity.TransactionEntity;
import com.james.medpay.features.transaction.domain.entity.TransactionPayerDetail;
import com.james.medpay.features.transaction.domain.entity.TransactionPaymentDetail;
import com.james.medpay.features.transaction.domain.entity.contract.ITransactionEntity;
import com.james.medpay.features.transaction.domain.entity.enums.TransactionStatusEnum;
import com.james.medpay.features.transaction.domain.repository.contract.ITransactionEntityRepository;
import com.james.medpay.features.transaction.domain.repository.contract.ITransactionPayerDetailRepository;
import com.james.medpay.features.transaction.domain.repository.contract.ITransactionPaymentDetailRepository;
import com.james.medpay.features.transaction.domain.repository.dataRepository.ITransactionEntityDataRepository;
import com.james.medpay.features.users.domain.entity.UserEntity;
import com.james.medpay.features.users.domain.entity.contract.IUserEntity;
import com.james.medpay.features.users.domain.repository.contract.IUserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static com.james.medpay.features.transaction.domain.entity.enums.TransactionStatusEnum.FAILED;
import static com.james.medpay.features.transaction.domain.entity.enums.TransactionStatusEnum.SUCCESSFUL;
import static java.util.stream.Collectors.groupingBy;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@RepositoryImpl
@RequiredArgsConstructor
public class TransactionEntityRepositoryImpl implements ITransactionEntityRepository {

	public static final String HOSPITAL_NOT_FOUND = "Error, Invalid hospital detail supplied.";
	public static final String USER_NOT_FOUND = "Error, Invalid user detail supplied.";
	public static final String PAYER_IS_INVALID = "Error, Invalid payer detail supplied.";
	public static final String PAYMENT_NOT_VALID = "Error, Invalid payment details";
	public static final String TRANSACTION_FAILED = "Failed to create transaction :";
	public static final String TRANSACTION_FAILED_API = "Failed to receive response from third party";
	public static final String BILL_NOT_PRESENT = "Bill number is required for this transaction";

	private final IHospitalBasicDetailRepository hospitalRepository;
	private final IUserEntityRepository userRepository;
	private final ITransactionEntityDataRepository dataRepository;
	private final ITransactionPayerDetailRepository payerDetailRepository;
	private final ITransactionPaymentDetailRepository paymentDetailRepository;
	private final TransactionReceiptExporter transactionReceiptExporter;
	private final IHospitalApiUsageRepository apiUsageRepository;
	private final IPatientBillUsecase billUsecase;

	private final List<String> message = new ArrayList<>();

	@Override
	public Page<TransactionEntity> getInPagesBySpecification( Specification<TransactionEntity> specification, Pageable pageable ){
		return this.dataRepository.findAll( specification, pageable );
	}

	@Override
	public List<TransactionEntity> getAllBySpecification(Specification<TransactionEntity> specification){
		return this.dataRepository.findAll( specification );
	}

	@Transactional
	@Override
	public ITransactionEntity handleCreateTransaction( TransactionEntity transaction ) {
		Optional<HospitalEntity> optionalHospital = this.hospitalRepository.getHospitalById( transaction.getHospital().getId() );
		return _createTransactionIfHospitalIsPresentOrThrow( optionalHospital, transaction );
	}

	private TransactionEntity _createTransactionIfHospitalIsPresentOrThrow(
			Optional<HospitalEntity> optionalHospital, TransactionEntity transaction
	) {
		return optionalHospital.map( hospital -> _processBillOrServiceTransaction( hospital, transaction ) )
		                       .orElseThrow( () -> new InvalidRequestException( HOSPITAL_NOT_FOUND ) );
	}

	private TransactionEntity _processBillOrServiceTransaction( HospitalEntity hospital, TransactionEntity unSavedTransaction ) {
		if ( isNotEmpty( hospital.getHasHospitalSoftware() ) && hospital.getHasHospitalSoftware() ) {
			return _processBillTransaction( hospital, unSavedTransaction );
		}
		else {
			return _processServiceTransaction( hospital, unSavedTransaction );
		}
	}

	private TransactionEntity _processBillTransaction( HospitalEntity hospital, TransactionEntity unSavedTransaction ) {
		if ( !_isValidBillTransactionRequest( unSavedTransaction ) ) {
			throw new InvalidRequestException( Arrays.toString( message.toArray() ) );
		}
		boolean isNotifySuccess = _notifyThirdPartyAPIForBillPayment( hospital.getId(), unSavedTransaction.getPaymentDetail().getBillNumber() );
		_updateTransactionPartialEntity( unSavedTransaction, hospital, isNotifySuccess ? SUCCESSFUL : FAILED );
		return this.dataRepository.save( unSavedTransaction );
	}

	private TransactionEntity _processServiceTransaction( HospitalEntity hospital, TransactionEntity unSavedTransaction ) {
		if ( !_isValidServiceTransactionRequest( unSavedTransaction ) ) {
			throw new InvalidRequestException( Arrays.toString( message.toArray() ) );
		}
		_updateTransactionPartialEntity( unSavedTransaction, hospital, SUCCESSFUL );
		TransactionEntity savedTransaction = this.dataRepository.save(unSavedTransaction);
		// update bill status
		this.billUsecase.updateBillStatus(savedTransaction.getPaymentDetail().getBillNumber(), BillStatus.PAID);
		return savedTransaction;
	}

	private boolean _isValidBillTransactionRequest( TransactionEntity unSavedTransaction ) {
		boolean isBillNumberPresent = _isBillNumberPresent( unSavedTransaction );
		boolean isTheRequestUserValid = _isRequestUserValid( unSavedTransaction );
		return isBillNumberPresent && isTheRequestUserValid;
	}

	private boolean _isBillNumberPresent( TransactionEntity unSavedTransaction ) {
		TransactionPaymentDetail paymentDetail = unSavedTransaction.getPaymentDetail();
		boolean result = isNotEmpty( paymentDetail ) && isNotEmpty( paymentDetail.getBillNumber() );
		_addToValidationMessageIfCheckFailed( result, TRANSACTION_FAILED + BILL_NOT_PRESENT );
		return result;
	}

	private boolean _isRequestUserValid( TransactionEntity unSavedTransaction ) {
		Optional<IUserEntity> optional = this.userRepository.getUserById( unSavedTransaction.getUser().getId() );
		boolean result = optional.isPresent() && isNotEmpty( optional.get().getId() );
		_addToValidationMessageIfCheckFailed( result, TRANSACTION_FAILED + USER_NOT_FOUND );
		return result;
	}

	private void _updateTransactionPartialEntity(
			TransactionEntity transaction, HospitalEntity hospital, TransactionStatusEnum statusEnum
	) {
		transaction.setHospital( hospital );
		transaction.setStatusEnum( statusEnum );
		_createPayerDetailsForTransaction( transaction );
		_createPaymentDetailsForTransaction( transaction );
	}

	private void _createPayerDetailsForTransaction( TransactionEntity unSavedTransaction ) {
		TransactionPayerDetail detail = ( TransactionPayerDetail ) this.payerDetailRepository.createPayerDetail(
				unSavedTransaction.getPayerDetail() );
		unSavedTransaction.setPayerDetail( detail );
	}

	private void _createPaymentDetailsForTransaction( TransactionEntity unSavedTransaction ) {
		TransactionPaymentDetail paymentDetail = ( TransactionPaymentDetail ) this.paymentDetailRepository.createPaymentDetail(
				unSavedTransaction.getPaymentDetail() );
		unSavedTransaction.setPaymentDetail( paymentDetail );
	}

	private boolean _isValidServiceTransactionRequest( TransactionEntity unSavedTransaction ) {
		boolean isValidPayer = _isPayerDetailValid( unSavedTransaction );
		boolean isValidPayment = _isPaymentDetailValid( unSavedTransaction );
		boolean isValidUser = _isRequestUserValid( unSavedTransaction );
		return isValidPayer && isValidPayment && isValidUser;
	}

	private boolean _isPayerDetailValid( TransactionEntity unSavedTransaction ) {
		TransactionPayerDetail payerDetail = unSavedTransaction.getPayerDetail();
		boolean result = isNotEmpty( payerDetail ) && isNotEmpty( payerDetail.getFullName() );
		_addToValidationMessageIfCheckFailed( result, TRANSACTION_FAILED + PAYER_IS_INVALID );
		return result;
	}

	private boolean _isPaymentDetailValid( TransactionEntity unSavedTransaction ) {
		TransactionPaymentDetail paymentDetail = unSavedTransaction.getPaymentDetail();
		boolean result = isNotEmpty( paymentDetail ) && _hasAtLeastOneServiceItemInPaymentDetail( paymentDetail );
		_addToValidationMessageIfCheckFailed( result, TRANSACTION_FAILED + PAYMENT_NOT_VALID );
		return result;
	}

	private boolean _hasAtLeastOneServiceItemInPaymentDetail( TransactionPaymentDetail unSavedPaymentDetail ) {
		return isNotEmpty( unSavedPaymentDetail ) && isNotEmpty( unSavedPaymentDetail.getServices() ) && !unSavedPaymentDetail.getServices()
		                                                                                                                      .isEmpty();
	}

	private boolean _notifyThirdPartyAPIForBillPayment( Long hospitalId, String billNumber ) {
		Optional<Boolean> result = this.apiUsageRepository.apiSendBillPayment( hospitalId, billNumber );
		return result.isPresent() && result.get();
	}

	// todo:: remove unnecessary methods

	@Override
	public Optional<ITransactionEntity> getById( Long transactionId ) {
		return _findByIdFromDataRepository( transactionId ).map( transactionEntity -> transactionEntity );
	}

	@Override
	public List<ITransactionEntity> getAllByReference( String reference ) {
		return new ArrayList<>( _findByReferenceFromDataRepository( reference ) );
	}

	@Override
	public Optional<byte[]> getReceiptPdfBytesByReferenceAndToken( String reference, String token ) {
		Optional<TransactionEntity> optional = this.dataRepository.findByReferenceEqualsAndTokenEquals( reference, token );
		return optional.map( this.transactionReceiptExporter::export );
	}

	@Override
	public List<ITransactionEntity> getAll() {
		return new ArrayList<>( dataRepository.findAll() );
	}

	@Override
	public PageParam getAll( Pageable pageable ) {
		return null;
	}

	@Override
	public List<ITransactionEntity> getByHospitalAndDateRange(
			Long hospitalId, DateRange dateRange
	) {
		HospitalEntity hospital = HospitalEntity.builder().id( hospitalId ).build();
		List<TransactionEntity> list = this.dataRepository.findAllByHospitalAndDateIsLessThanEqualAndDateGreaterThanEqual(
				hospital, dateRange.getEndDate(), dateRange.getEndDate() );
		return new ArrayList<>( list );
	}

	@Override
	public List<ITransactionEntity> getByDateRange( DateRange dateRange ) {
		List<TransactionEntity> list = this.dataRepository.findAllByDateIsLessThanEqualAndDateGreaterThanEqual(
				dateRange.getEndDate(), dateRange.getStartDate() );
		return new ArrayList<>( list );
	}

	@Override
	public List<ITransactionEntity> getByHospitalAndDateRangeAndAmountRange(
			Long hospitalId, DateRange dateRange, AmountRange amountRange
	) {
		HospitalEntity hospital = HospitalEntity.builder().id( hospitalId ).build();
		List<TransactionEntity> list = this.dataRepository.findAllByHospitalAndDateIsLessThanEqualAndDateIsGreaterThanEqualAndAmountIsLessThanEqualAndAmountIsGreaterThanEqual(
				hospital, dateRange.getEndDate(), dateRange.getStartDate(), amountRange.getMaxAmount(),
				amountRange.getMinAmount()
		);
		return new ArrayList<>( list );
	}

	@Override
	public Optional<Boolean> getHasNotifiedHospital( Long hospitalId ) {
		Optional<TransactionEntity> optional = this._findByIdFromDataRepository( hospitalId );
		return optional.map( TransactionEntity::getHasNotifiedHospital );
	}

	@Override
	public List<ITransactionEntity> getByUserAndHospitalAndDateRange(
			Long userId, Long hospitalId, DateRange dateRange
	) {
		List<TransactionEntity> result = this.dataRepository.findAllByUserAndHospitalAndDateIsLessThanEqualAndDateGreaterThanEqual(
				new UserEntity( userId ), new HospitalEntity( hospitalId ), dateRange.getEndDate(), dateRange.getStartDate() );
		return new ArrayList<>( result );
	}

	@Override
	public Optional<ITransactionEntity> getOneOnlyByReference( String reference ) {
		return _findOneByReferenceFromDataRepository( reference ).map( entity -> entity );
	}

	@Override
	public Optional<TransactionStatusEnum> getStatusByReference(
			String reference
	) {
		Optional<TransactionEntity> optional = this._findOneByReferenceFromDataRepository( reference );
		return optional.map( TransactionEntity::getStatusEnum );
	}

	@Override
	public Optional<TransactionStatusEnum> getStatusById( Long transactionId ) {
		Optional<TransactionEntity> optional = this._findByIdFromDataRepository( transactionId );
		return optional.map( TransactionEntity::getStatusEnum );
	}

	@Override
	public Optional<ITransactionEntity> getByReferenceAndToken( String reference, String token ) {
		return this.dataRepository.findByReferenceEqualsAndTokenEquals( reference, token ).map( entity -> entity );
	}

	@Override
	public Map<LocalDate, List<TransactionEntity>> getRecentTransactionsGroupByDate( Long hospitalId, Long userId ) {
		Optional<HospitalEntity> optionalHospitalEntity = this.hospitalRepository.getHospitalById( hospitalId );
		return optionalHospitalEntity.map( hospital -> _getTransactionsIfUserIsPresentOrThrow( hospital, userId ) )
		                             .orElseThrow( () -> new InvalidRequestException( HOSPITAL_NOT_FOUND ) );
	}

	private Map<LocalDate, List<TransactionEntity>> _getTransactionsIfUserIsPresentOrThrow( HospitalEntity hospital, Long userId ) {
		Optional<IUserEntity> optionalIUserEntity = this.userRepository.getUserById( userId );
		return optionalIUserEntity.map( user -> _getUsersRecentTransactions( hospital, user ) )
		                          .orElseThrow( () -> new InvalidRequestException( USER_NOT_FOUND ) );
	}

	private Map<LocalDate, List<TransactionEntity>> _getUsersRecentTransactions( HospitalEntity hospital, IUserEntity user ) {
		LocalDate date = LocalDate.now();
		List<TransactionEntity> list = this.dataRepository
				.findAllByHospitalAndUserAndDateIsBetween( hospital, ( UserEntity ) user, date.minusDays( 5 ), date );
		return list.stream().peek( TransactionEntity::trim ).collect( groupingBy( TransactionEntity::getDate ) );

	}

	private Optional<TransactionEntity> _findByIdFromDataRepository( Long id ) {
		return this.dataRepository.findById( id );
	}

	private List<TransactionEntity> _findByReferenceFromDataRepository( String reference ) {
		return this.dataRepository.findAllByReferenceContainsIgnoreCase( reference );
	}

	private Optional<TransactionEntity> _findOneByReferenceFromDataRepository( String reference ) {
		return this.dataRepository.findByReferenceEquals( reference );
	}

	private void _addToValidationMessageIfCheckFailed( boolean isValid, String message ) {
		if ( !isValid ) {
			this.message.add( message );
		}
	}
}
