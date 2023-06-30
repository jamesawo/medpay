
package com.james.medpay.features.transaction.domain.usecase.payer;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.transaction.domain.entity.contract.ITransactionPayerDetail;
import com.james.medpay.features.transaction.domain.repository.contract.ITransactionPayerDetailRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class TransactionGetPayerDetailByTrIdUsecase implements
                                                    UsecaseContract<Optional<ITransactionPayerDetail>, Long> {
	private final ITransactionPayerDetailRepository repository;

	@Override
	public Optional<ITransactionPayerDetail> execute( Long hospitalId ) {
		return repository.getPayerDetailByTransactionId( hospitalId );
	}
}
