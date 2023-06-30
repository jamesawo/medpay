package com.james.medpay.features.transaction.data.interactor.implementation;

import com.james.medpay.core.annotation.Interactor;
import com.james.medpay.core.contract.DataMapper;
import com.james.medpay.features.transaction.data.interactor.contract.TransactionPaymentInteractor;
import com.james.medpay.features.transaction.data.request.TransactionPaymentDetailRequest;
import com.james.medpay.features.transaction.domain.entity.TransactionPaymentDetail;
import com.james.medpay.features.transaction.domain.entity.contract.ITransactionPaymentDetail;
import com.james.medpay.features.transaction.domain.entity.enums.TransactionSearchBy;
import com.james.medpay.features.transaction.domain.usecase.payment.TransactionGetPaymentDetailByTrIdUsecase;
import com.james.medpay.features.transaction.domain.usecase.payment.TransactionGetPaymentDetailByTrRefUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.springframework.http.ResponseEntity.ok;

@Interactor
@RequiredArgsConstructor
public class TransactionPaymentInteractorImpl implements TransactionPaymentInteractor, DataMapper<ITransactionPaymentDetail, TransactionPaymentDetailRequest> {
	private final TransactionGetPaymentDetailByTrIdUsecase getPaymentDetailByTrIdUsecase;
	private final TransactionGetPaymentDetailByTrRefUsecase getPaymentDetailByTrRefUsecase;


	@Override
	public ResponseEntity<Optional<TransactionPaymentDetailRequest>> getPayerDetailsByTransactionReferenceOrId(
			TransactionSearchBy searchBy,
			String value
	) {
		switch ( searchBy ) {
			case TRN_ID:
				return getByTransactionId( Long.valueOf( value ) );
			case TRN_REFERENCE:
				return getByTransactionReference( value );
			default:
				return ok().body( empty() );
		}
	}


	@Override
	public ResponseEntity<Optional<TransactionPaymentDetailRequest>> getByTransactionId(
			Long transaction
	) {
		Optional<ITransactionPaymentDetail> optional = getPaymentDetailByTrIdUsecase.execute( transaction );
		return ResponseEntity.ok().body( optional.map( this::toRequest ) );
	}

	@Override
	public ResponseEntity<Optional<TransactionPaymentDetailRequest>> getByTransactionReference( String reference ) {
		Optional<ITransactionPaymentDetail> optional = getPaymentDetailByTrRefUsecase.execute( reference );
		return ResponseEntity.ok().body( optional.map( this::toRequest ) );
	}

	@Override
	public ITransactionPaymentDetail toEntity(
			TransactionPaymentDetailRequest request
	) {
		return TransactionPaymentDetailRequest.toEntity( request );
	}

	@Override
	public TransactionPaymentDetailRequest toRequest(
			ITransactionPaymentDetail detail
	) {
		TransactionPaymentDetail paymentDetail = ( TransactionPaymentDetail ) detail;
		return TransactionPaymentDetailRequest.toRequest( paymentDetail );
	}
}
