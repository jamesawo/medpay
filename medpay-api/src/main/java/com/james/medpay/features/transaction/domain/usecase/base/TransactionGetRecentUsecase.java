
package com.james.medpay.features.transaction.domain.usecase.base;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.transaction.domain.entity.TransactionEntity;
import com.james.medpay.features.transaction.domain.repository.contract.ITransactionEntityRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@UseCase
@RequiredArgsConstructor
public class TransactionGetRecentUsecase implements UsecaseContract<Map<LocalDate, List<TransactionEntity>>, Param<Long>> {
	private final ITransactionEntityRepository repository;

	@Override
	public Map<LocalDate, List<TransactionEntity>> execute( Param<Long> param ) {
		return repository.getRecentTransactionsGroupByDate( param.getId(), param.getModel() );
	}
}
