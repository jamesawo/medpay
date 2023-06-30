
package com.james.medpay.features.transaction.domain.usecase.settlement;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.transaction.domain.entity.contract.ITransactionSettlement;
import com.james.medpay.features.transaction.domain.repository.contract.ITransactionSettlementRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class TransactionGetSettlementByTrIdUsecase implements UsecaseContract<Optional<ITransactionSettlement>, Long> {
	private ITransactionSettlementRepository repository;

	@Override
	public Optional<ITransactionSettlement> execute( Long aLong ) {
		return repository.getByTransactionId( aLong );
	}
}
