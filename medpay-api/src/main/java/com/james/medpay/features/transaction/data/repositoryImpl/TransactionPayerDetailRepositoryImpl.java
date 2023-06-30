
package com.james.medpay.features.transaction.data.repositoryImpl;

import com.james.medpay.core.annotation.RepositoryImpl;
import com.james.medpay.features.transaction.domain.entity.TransactionEntity;
import com.james.medpay.features.transaction.domain.entity.TransactionPayerDetail;
import com.james.medpay.features.transaction.domain.entity.contract.ITransactionPayerDetail;
import com.james.medpay.features.transaction.domain.repository.contract.ITransactionPayerDetailRepository;
import com.james.medpay.features.transaction.domain.repository.dataRepository.ITransactionEntityDataRepository;
import com.james.medpay.features.transaction.domain.repository.dataRepository.ITransactionPayerDetailDataRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RepositoryImpl
@RequiredArgsConstructor
public class TransactionPayerDetailRepositoryImpl implements ITransactionPayerDetailRepository {

	private final ITransactionEntityDataRepository dataRepository;
	private final ITransactionPayerDetailDataRepository payerDetailDataRepository;


	@Override
	public ITransactionPayerDetail createPayerDetail( TransactionPayerDetail detail ) {
		return this.payerDetailDataRepository.save( detail );
	}

	@Override
	public Optional<ITransactionPayerDetail> getPayerDetailByTransactionId( Long transactionId ) {
		Optional<TransactionEntity> optional = this._findByIdFromDataRepository( transactionId );
		return optional.map( TransactionEntity::getPayerDetail );
	}

	@Override
	public Optional<ITransactionPayerDetail> getPayerDetailByTransactionReference( String reference ) {
		Optional<TransactionEntity> optional = this._findByReferenceFromDataRepository( reference );
		return optional.map( TransactionEntity::getPayerDetail );
	}

	private Optional<TransactionEntity> _findByIdFromDataRepository( Long id ) {
		return this.dataRepository.findById( id );
	}

	private Optional<TransactionEntity> _findByReferenceFromDataRepository( String reference ) {
		return this.dataRepository.findByReferenceEquals( reference );
	}
}
