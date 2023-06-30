
package com.james.medpay.features.transaction.domain.repository.contract;

import com.james.medpay.features.transaction.domain.entity.TransactionSettlement;
import com.james.medpay.features.transaction.domain.entity.contract.ITransactionSettlement;

import java.util.Optional;

public interface ITransactionSettlementRepository {

	ITransactionSettlement createSettlement( TransactionSettlement settlement );
	
	Optional<ITransactionSettlement> getByTransactionId( Long id );

	Optional<ITransactionSettlement> getByTransactionReference( String reference );

	Optional<ITransactionSettlement> updateByTransactionId( Long transactionId, ITransactionSettlement settlement );

}

