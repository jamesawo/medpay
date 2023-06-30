package com.james.medpay.features.transaction.domain.usecase.base;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.transaction.domain.entity.contract.ITransactionEntity;
import com.james.medpay.features.transaction.domain.repository.contract.ITransactionEntityRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;


@UseCase
@RequiredArgsConstructor
public class TransactionGetOneOnlyByReferenceUsecase implements UsecaseContract<Optional<ITransactionEntity>, String> {
	private final ITransactionEntityRepository repository;

	@Override
	public Optional<ITransactionEntity> execute( String transactionReference ) {
		return repository.getOneOnlyByReference( transactionReference );
	}
}
