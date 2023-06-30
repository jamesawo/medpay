package com.james.medpay.features.transaction.data.interactor.implementation;

import com.james.medpay.core.annotation.Interactor;
import com.james.medpay.core.contract.DataMapper;
import com.james.medpay.features.transaction.data.interactor.contract.TransactionPayerInteractor;
import com.james.medpay.features.transaction.data.request.TransactionPayerDetailRequest;
import com.james.medpay.features.transaction.domain.entity.TransactionPayerDetail;
import com.james.medpay.features.transaction.domain.entity.contract.ITransactionPayerDetail;
import com.james.medpay.features.transaction.domain.entity.enums.TransactionSearchBy;
import com.james.medpay.features.transaction.domain.usecase.payer.TransactionGetPayerDetailByTrIdUsecase;
import com.james.medpay.features.transaction.domain.usecase.payer.TransactionGetPayerDetailByTrRefUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.springframework.http.ResponseEntity.ok;

@Interactor
@RequiredArgsConstructor
public class TransactionPayerInteractorImpl implements TransactionPayerInteractor, DataMapper<ITransactionPayerDetail, TransactionPayerDetailRequest> {

	private final TransactionGetPayerDetailByTrIdUsecase getPayerDetailByTrIdUsecase;
	private final TransactionGetPayerDetailByTrRefUsecase getPayerDetailByTrRefUsecase;


	@Override
	public ResponseEntity<Optional<TransactionPayerDetailRequest>> getPayerDetailsByTransactionReferenceOrId(
			TransactionSearchBy searchBy,
			String value
	) {
		switch ( searchBy ) {
			case TRN_ID:
				return getPayerDetailByTransactionId( Long.valueOf( value ) );
			case TRN_REFERENCE:
				return getPayerDetailByTransactionReference( value );
			default:
				return ok().body( empty() );
		}
	}

	@Override
	public ResponseEntity<Optional<TransactionPayerDetailRequest>> getPayerDetailByTransactionId(
			Long transactionId
	) {
		Optional<ITransactionPayerDetail> optional = this.getPayerDetailByTrIdUsecase.execute( transactionId );
		return ResponseEntity.ok().body( optional.map( this::toRequest ) );
	}

	@Override
	public ResponseEntity<Optional<TransactionPayerDetailRequest>> getPayerDetailByTransactionReference(
			String reference
	) {
		Optional<ITransactionPayerDetail> optional = this.getPayerDetailByTrRefUsecase.execute( reference );
		return ResponseEntity.ok().body( optional.map( this::toRequest ) );
	}

	@Override
	public ITransactionPayerDetail toEntity(
			TransactionPayerDetailRequest request
	) {
		return TransactionPayerDetailRequest.toEntity( request );
	}

	@Override
	public TransactionPayerDetailRequest toRequest(
			ITransactionPayerDetail entity
	) {
		TransactionPayerDetail detail = ( TransactionPayerDetail ) entity;
		return TransactionPayerDetailRequest.toRequest( detail );
	}
}
