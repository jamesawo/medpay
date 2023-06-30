
package com.james.medpay.features.transaction.domain.usecase.base;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.transaction.domain.entity.TransactionEntity;
import com.james.medpay.features.transaction.domain.entity.contract.ITransactionEntity;
import com.james.medpay.features.transaction.domain.repository.contract.ITransactionEntityRepository;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class TransactionSaveNewUsecase implements UsecaseContract<ITransactionEntity, TransactionEntity> {
	private final ITransactionEntityRepository repository;

	@Override
	public ITransactionEntity execute(
			TransactionEntity transaction
	) {
		return repository.handleCreateTransaction( transaction );
	}
}
