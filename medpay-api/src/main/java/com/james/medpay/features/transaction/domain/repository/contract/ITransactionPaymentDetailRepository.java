
package com.james.medpay.features.transaction.domain.repository.contract;

import com.james.medpay.features.transaction.domain.entity.TransactionPaymentDetail;
import com.james.medpay.features.transaction.domain.entity.contract.ITransactionPaymentDetail;

import java.util.Optional;

public interface ITransactionPaymentDetailRepository {

	ITransactionPaymentDetail createPaymentDetail( TransactionPaymentDetail detail );

	Optional<ITransactionPaymentDetail> getByTransactionId( Long transaction );

	Optional<ITransactionPaymentDetail> getByTransactionReference( String reference );

}
