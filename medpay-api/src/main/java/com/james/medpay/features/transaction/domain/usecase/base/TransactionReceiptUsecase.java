
package com.james.medpay.features.transaction.domain.usecase.base;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.BiParam;
import com.james.medpay.features.transaction.domain.repository.contract.ITransactionEntityRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class TransactionReceiptUsecase implements UsecaseContract<Optional<byte[]>, BiParam<String, String>> {
	private final ITransactionEntityRepository repository;


	@Override
	public Optional<byte[]> execute( BiParam<String, String> param ) {
		return repository.getReceiptPdfBytesByReferenceAndToken( param.getLeft(), param.getRight() );
	}
}
