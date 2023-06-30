
package com.james.medpay.features.transaction.domain.usecase.base;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.transaction.domain.repository.contract.ITransactionEntityRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class TransactionGetHasNotifiedHospitalUsecase implements UsecaseContract<Optional<Boolean>, Long> {
	private final ITransactionEntityRepository repository;

	@Override
	public Optional<Boolean> execute( Long hospitalId ) {
		return repository.getHasNotifiedHospital( hospitalId );
	}
}
