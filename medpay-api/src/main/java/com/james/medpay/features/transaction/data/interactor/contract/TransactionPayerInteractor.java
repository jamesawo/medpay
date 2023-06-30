package com.james.medpay.features.transaction.data.interactor.contract;

import com.james.medpay.features.transaction.data.request.TransactionPayerDetailRequest;
import com.james.medpay.features.transaction.domain.entity.enums.TransactionSearchBy;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface TransactionPayerInteractor {

	ResponseEntity<Optional<TransactionPayerDetailRequest>> getPayerDetailsByTransactionReferenceOrId(
			TransactionSearchBy searchBy,
			String value
	);

	ResponseEntity<Optional<TransactionPayerDetailRequest>> getPayerDetailByTransactionId( Long transactionId );

	ResponseEntity<Optional<TransactionPayerDetailRequest>> getPayerDetailByTransactionReference( String reference );

}
