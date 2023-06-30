
package com.james.medpay.features.transaction.domain.usecase.payment;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.transaction.domain.entity.contract.ITransactionPaymentDetail;
import com.james.medpay.features.transaction.domain.repository.contract.ITransactionPaymentDetailRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class TransactionGetPaymentDetailByTrIdUsecase implements UsecaseContract<Optional<ITransactionPaymentDetail>, Long> {
	private final ITransactionPaymentDetailRepository repository;

	@Override
	public Optional<ITransactionPaymentDetail> execute( Long aLong ) {
		return repository.getByTransactionId( aLong );
	}
}
