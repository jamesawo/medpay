
package com.james.medpay.features.transaction.domain.usecase.base;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.BiParam;
import com.james.medpay.features.transaction.domain.entity.contract.ITransactionEntity;
import com.james.medpay.features.transaction.domain.repository.contract.ITransactionEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@UseCase
@RequiredArgsConstructor
public class TransactionGetManyUsecase implements UsecaseContract<
		Page<ITransactionEntity>,
		BiParam<Specification<ITransactionEntity>, Pageable>
		> {

	private final ITransactionEntityRepository repository;


	@Override
	public Page<ITransactionEntity> execute(
			BiParam<Specification<ITransactionEntity>, Pageable> param
	) {
		return null;
	}
}
