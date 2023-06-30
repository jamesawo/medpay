
package com.james.medpay.features.transaction.domain.repository.contract;

import com.james.medpay.core.params.AmountRange;
import com.james.medpay.core.params.DateRange;
import com.james.medpay.core.params.PageParam;
import com.james.medpay.features.transaction.domain.entity.TransactionEntity;
import com.james.medpay.features.transaction.domain.entity.contract.ITransactionEntity;
import com.james.medpay.features.transaction.domain.entity.enums.TransactionStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ITransactionEntityRepository {

	Page<TransactionEntity> getInPagesBySpecification( Specification<TransactionEntity> specification, Pageable pageable );

	List<TransactionEntity> getAllBySpecification( Specification<TransactionEntity> specification );

	ITransactionEntity handleCreateTransaction( TransactionEntity transaction );

	Map<LocalDate, List<TransactionEntity>> getRecentTransactionsGroupByDate( Long hospitalId, Long userId);

	Optional<byte[]> getReceiptPdfBytesByReferenceAndToken( String reference, String token );

	Optional<ITransactionEntity> getById( Long transactionId );

	List<ITransactionEntity> getAllByReference( String reference );

	List<ITransactionEntity> getAll();

	PageParam getAll( Pageable pageable );

	List<ITransactionEntity> getByHospitalAndDateRange( Long hospitalId, DateRange dateRange );

	List<ITransactionEntity> getByDateRange( DateRange dateRange );

	List<ITransactionEntity> getByHospitalAndDateRangeAndAmountRange( Long hospitalId, DateRange dateRange, AmountRange amountRange );

	Optional<Boolean> getHasNotifiedHospital( Long hospitalId );

	List<ITransactionEntity> getByUserAndHospitalAndDateRange( Long userId, Long hospitalId, DateRange dateRange );

	Optional<ITransactionEntity> getOneOnlyByReference( String reference );

	Optional<TransactionStatusEnum> getStatusByReference( String reference );

	Optional<TransactionStatusEnum> getStatusById( Long transactionId );

	Optional<ITransactionEntity> getByReferenceAndToken( String reference, String token );
}
