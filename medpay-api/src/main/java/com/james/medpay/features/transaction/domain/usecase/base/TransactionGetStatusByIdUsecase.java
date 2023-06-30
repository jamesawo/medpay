
package com.james.medpay.features.transaction.domain.usecase.base;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.transaction.domain.entity.enums.TransactionStatusEnum;
import com.james.medpay.features.transaction.domain.repository.contract.ITransactionEntityRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class TransactionGetStatusByIdUsecase implements UsecaseContract<Optional<TransactionStatusEnum>, Long> {
	private final ITransactionEntityRepository repository;

	@Override
	public Optional<TransactionStatusEnum> execute( Long transactionId ) {
		return repository.getStatusById( transactionId );
	}
}
