
package com.james.medpay.features.transaction.domain.usecase.settlement;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.transaction.domain.entity.contract.ITransactionSettlement;
import com.james.medpay.features.transaction.domain.repository.contract.ITransactionSettlementRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class TransactionUpdateSettlementByTrIdUsecase implements UsecaseContract<Optional<ITransactionSettlement>, Param<ITransactionSettlement>> {
	private ITransactionSettlementRepository repository;


	@Override
	public Optional<ITransactionSettlement> execute(
			Param<ITransactionSettlement> param
	) {
		return repository.updateByTransactionId( param.getId(), param.getModel() );
	}
}
