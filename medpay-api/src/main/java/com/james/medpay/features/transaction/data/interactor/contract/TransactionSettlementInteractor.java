package com.james.medpay.features.transaction.data.interactor.contract;

import com.james.medpay.features.transaction.data.request.TransactionSettlementRequest;
import com.james.medpay.features.transaction.domain.entity.contract.ITransactionSettlement;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface TransactionSettlementInteractor {
	ResponseEntity<Optional<TransactionSettlementRequest>> getByTransactionId( Long id );

	ResponseEntity<Optional<TransactionSettlementRequest>> getByTransactionReference( String reference );

	ResponseEntity<Optional<TransactionSettlementRequest>> updateByTransactionId(
			Long transactionId, ITransactionSettlement settlement
	);

}
