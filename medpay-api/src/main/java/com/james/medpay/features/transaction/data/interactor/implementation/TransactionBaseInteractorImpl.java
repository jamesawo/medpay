package com.james.medpay.features.transaction.data.interactor.implementation;

import com.james.medpay.core.annotation.Interactor;
import com.james.medpay.core.contract.DataMapper;
import com.james.medpay.core.exception.ApplicationException;
import com.james.medpay.core.params.*;
import com.james.medpay.features.transaction.data.interactor.contract.TransactionBaseInteractor;
import com.james.medpay.features.transaction.data.request.TransactionRecentRequest;
import com.james.medpay.features.transaction.data.request.TransactionRequest;
import com.james.medpay.features.transaction.data.request.searchRequest.TransactionSearchRequest;
import com.james.medpay.features.transaction.domain.entity.TransactionEntity;
import com.james.medpay.features.transaction.domain.entity.contract.ITransactionEntity;
import com.james.medpay.features.transaction.domain.entity.enums.TransactionSearchBy;
import com.james.medpay.features.transaction.domain.entity.enums.TransactionStatusEnum;
import com.james.medpay.features.transaction.domain.usecase.base.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.james.medpay.features.transaction.data.request.specification.TransactionSearchSpecification.byCriteria;
import static java.util.Optional.empty;
import static org.springframework.http.ResponseEntity.ok;

@Interactor
@RequiredArgsConstructor
public class TransactionBaseInteractorImpl implements TransactionBaseInteractor, DataMapper<ITransactionEntity, TransactionRequest> {

	public static final String RECEIPT_NOT_FOUND = "Receipt not found, Invalid details supplied";
	public static final String RECEIPT_FILE_NAME = "inline; filename=\"Receipt.pdf\"";
	public static final String PDF_CONTENT_TYPE = "application/pdf; charset=UTF-8";

	private final TransactionGetManyNoSpecificationUsecase getManyNoSpecificationUsecase;
	private final TransactionGetOneByIdUsecase getOneByIdUsecase;
	private final TransactionReceiptUsecase receiptUsecase;
	private final TransactionSaveNewUsecase saveNewUsecase;
	private final TransactionGetOneOnlyByReferenceUsecase getOneOnlyByReferenceUsecase;
	private final TransactionGetStatusByReferenceUsecase getStatusByReferenceUsecase;
	private final TransactionGetStatusByIdUsecase getStatusByIdUsecase;
	private final TransactionGetOneByRefAndTokenUsecase getOneByRefAndTokenUsecase;
	private final TransactionGetRecentUsecase getRecentUsecase;
	private final TransactionGetInPagesBySpecificationUsecase specificationUsecase;


	@Override
	public ResponseEntity<TransactionRequest> createTransaction(
			TransactionRequest transaction
	) {
		ITransactionEntity result = this.saveNewUsecase.execute( ( TransactionEntity ) toEntity( transaction ) );
		result.trim();
		return ok().body( toRequest( result ) );
	}

	@Override
	public ResponseEntity<List<TransactionRecentRequest>> getRecentTransactions( Long hospitalId, Long userId ) {
		Map<LocalDate, List<TransactionEntity>> list = this.getRecentUsecase.execute( new Param<>( hospitalId, userId ) );
		List<TransactionRecentRequest> collect = list.entrySet().stream().map( TransactionRecentRequest::toRequest ).collect(
				Collectors.toList() );
		return ok().body( collect );
	}

	@Override
	public ResponseEntity<PageResult<TransactionRequest>> getTransactionBySearchRequest( PageSearchRequest<TransactionSearchRequest> requestPage ) {
		Page<TransactionEntity> page = this.specificationUsecase.execute( new BiParam<>( byCriteria( requestPage.getSearchRequest() ), requestPage.getPage().toPageable() ) );
		List<TransactionRequest> collect = page.getContent().stream().map( TransactionRequest::trimToRequest ).collect( Collectors.toList() );
		return ok().body( new PageResult<>(new PageParam().fromPage( page ), collect) );
	}

	@Override
	public ResponseEntity<Optional<TransactionRequest>> getTransactionByReferenceOrId( TransactionSearchBy searchBy, String value, boolean trim ) {
		switch ( searchBy ) {
			case TRN_ID:
				return getById( Long.valueOf( value ), trim );
			case TRN_REFERENCE:
				return getByReference( value, trim );
			default:
				return ok().body( empty() );
		}
	}

	@Override
	public ResponseEntity<Optional<TransactionStatusEnum>> getTransactionStatusByReferenceOrId(
			TransactionSearchBy searchBy, String value
	) {
		switch ( searchBy ) {
			case TRN_ID:
				return getTransactionStatusById( Long.valueOf( value ) );
			case TRN_REFERENCE:
				return getTransactionStatusByReference( value );
			default:
				return ok().body( empty() );
		}
	}

	@Override
	public ResponseEntity<Optional<TransactionStatusEnum>> getTransactionStatusByReference(
			String reference
	) {
		Optional<TransactionStatusEnum> optional = this.getStatusByReferenceUsecase.execute( reference );
		return ok().body( optional );
	}

	@Override
	public ResponseEntity<Optional<TransactionStatusEnum>> getTransactionStatusById(
			Long transactionId
	) {
		Optional<TransactionStatusEnum> optional = this.getStatusByIdUsecase.execute( transactionId );
		return ok().body( optional );
	}

	@Override
	public ResponseEntity<Optional<TransactionRequest>> getByReference( String reference, boolean trim ) {
		Optional<ITransactionEntity> result = this.getOneOnlyByReferenceUsecase.execute( reference );
		return ok().body( result.map( transaction -> _trimAndMapToRequest( transaction, trim ) ) );
	}

	private TransactionRequest _trimAndMapToRequest( ITransactionEntity transaction, boolean trim ) {
		if ( trim ) {
			return TransactionRequest.trimToRequest( ( TransactionEntity ) transaction );
		}
		return toRequest( transaction );
	}

	@Override
	public ResponseEntity<Optional<TransactionRequest>> getById( Long transactionId, boolean trim ) {
		Optional<ITransactionEntity> result = this.getOneByIdUsecase.execute( transactionId );
		return ok().body( result.map( transaction -> _trimAndMapToRequest( transaction, trim ) ) );
	}

	@Override
	public ResponseEntity<byte[]> getTransactionReceiptPdfBytes( String reference, String token ) {
		Optional<byte[]> optionalBytes = this.receiptUsecase.execute( new BiParam<>( reference, token ) );
		return optionalBytes.map( bytes -> ok()
				.header( HttpHeaders.CONTENT_TYPE, PDF_CONTENT_TYPE )
				.header( HttpHeaders.CONTENT_DISPOSITION, RECEIPT_FILE_NAME )
				.body( optionalBytes.get() ) ).orElseThrow( ()->new ApplicationException( RECEIPT_NOT_FOUND )
		);
	}

	@Override
	public ResponseEntity<List<TransactionRequest>> getAll() {
		List<ITransactionEntity> list = this.getManyNoSpecificationUsecase.execute( new EmptyParam() );
		return ok().body( list.stream().map( this::toRequest ).collect( Collectors.toList() ) );
	}

	@Override
	public ResponseEntity<Optional<TransactionRequest>> getOneByReferenceAndToken( String reference, String token, boolean trim ) {
		Optional<ITransactionEntity> optional = this.getOneByRefAndTokenUsecase.execute( new BiParam<>( reference, token ) );
		return ok().body( optional.map( transaction -> _trimAndMapToRequest( transaction, trim ) ) );
	}

	@Override
	public ITransactionEntity toEntity( TransactionRequest request ) {
		return TransactionRequest.toEntity( request );
	}

	@Override
	public TransactionRequest toRequest( ITransactionEntity transaction ) {
		TransactionRequest request = TransactionRequest.toRequest( ( TransactionEntity ) transaction );
		_setReceiptDownloadUrl( request );
		return request;
	}

	private void _setReceiptDownloadUrl( TransactionRequest request ) {
		String link = _formatLink( request.getReference(), request.getToken() );
		request.setReceiptUrl( link );
	}

	private String _formatLink( String ref, String token ) {
		return TransactionRequest.getReceiptDownloadUrl(ref, token);
	}

}
