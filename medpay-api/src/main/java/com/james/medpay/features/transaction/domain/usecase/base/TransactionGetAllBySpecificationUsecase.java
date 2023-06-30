
package com.james.medpay.features.transaction.domain.usecase.base;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.transaction.domain.entity.TransactionEntity;
import com.james.medpay.features.transaction.domain.repository.contract.ITransactionEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class TransactionGetAllBySpecificationUsecase implements UsecaseContract<List<TransactionEntity>, Specification<TransactionEntity>> {

	private final ITransactionEntityRepository repository;

	@Override
	public List<TransactionEntity> execute( Specification<TransactionEntity> specification) {
		return repository.getAllBySpecification( specification );
	}
}
