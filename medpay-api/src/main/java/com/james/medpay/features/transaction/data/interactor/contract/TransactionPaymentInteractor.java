package com.james.medpay.features.transaction.data.interactor.contract;

import com.james.medpay.features.transaction.data.request.TransactionPaymentDetailRequest;
import com.james.medpay.features.transaction.domain.entity.enums.TransactionSearchBy;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface TransactionPaymentInteractor {
	ResponseEntity<Optional<TransactionPaymentDetailRequest>> getPayerDetailsByTransactionReferenceOrId(
			TransactionSearchBy searchBy,
			String value
	);

	ResponseEntity<Optional<TransactionPaymentDetailRequest>> getByTransactionId( Long transaction );

	ResponseEntity<Optional<TransactionPaymentDetailRequest>> getByTransactionReference( String reference );

}
