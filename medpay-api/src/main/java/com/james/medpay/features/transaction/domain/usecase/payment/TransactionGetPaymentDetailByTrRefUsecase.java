
package com.james.medpay.features.transaction.domain.usecase.payment;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.transaction.domain.entity.contract.ITransactionPaymentDetail;
import com.james.medpay.features.transaction.domain.repository.contract.ITransactionPaymentDetailRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class TransactionGetPaymentDetailByTrRefUsecase implements UsecaseContract<Optional<ITransactionPaymentDetail>, String> {
	private final ITransactionPaymentDetailRepository repository;

	@Override
	public Optional<ITransactionPaymentDetail> execute( String reference ) {
		return repository.getByTransactionReference( reference );
	}
}
