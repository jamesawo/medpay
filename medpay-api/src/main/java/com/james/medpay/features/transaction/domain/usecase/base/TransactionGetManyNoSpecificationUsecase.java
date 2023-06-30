
package com.james.medpay.features.transaction.domain.usecase.base;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.EmptyParam;
import com.james.medpay.features.transaction.domain.entity.contract.ITransactionEntity;
import com.james.medpay.features.transaction.domain.repository.contract.ITransactionEntityRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class TransactionGetManyNoSpecificationUsecase implements UsecaseContract<
		List<ITransactionEntity>, EmptyParam> {

	private final ITransactionEntityRepository repository;


	@Override
	public List<ITransactionEntity> execute( EmptyParam param ) {
		return repository.getAll();
	}
}
