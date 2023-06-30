
package com.james.medpay.features.transaction.domain.repository.contract;

import com.james.medpay.features.transaction.domain.entity.TransactionPayerDetail;
import com.james.medpay.features.transaction.domain.entity.contract.ITransactionPayerDetail;

import java.util.Optional;

public interface ITransactionPayerDetailRepository {

	ITransactionPayerDetail createPayerDetail( TransactionPayerDetail detail );

	Optional<ITransactionPayerDetail> getPayerDetailByTransactionId( Long transactionId );

	Optional<ITransactionPayerDetail> getPayerDetailByTransactionReference( String reference );


}
