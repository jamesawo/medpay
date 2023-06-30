
package com.james.medpay.features.transaction.domain.usecase.base;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.transaction.domain.entity.contract.ITransactionEntity;
import com.james.medpay.features.transaction.domain.repository.contract.ITransactionEntityRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;


@UseCase
@RequiredArgsConstructor
public class TransactionGetOneToListByReferenceUsecase implements UsecaseContract<List<ITransactionEntity>, String> {
	private final ITransactionEntityRepository repository;

	@Override
	public List<ITransactionEntity> execute( String transactionReference ) {
		return repository.getAllByReference( transactionReference );
	}
}
