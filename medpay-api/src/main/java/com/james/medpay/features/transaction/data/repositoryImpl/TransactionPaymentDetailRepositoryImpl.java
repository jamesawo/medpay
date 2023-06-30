
package com.james.medpay.features.transaction.data.repositoryImpl;

import com.james.medpay.core.annotation.RepositoryImpl;
import com.james.medpay.features.transaction.domain.entity.TransactionEntity;
import com.james.medpay.features.transaction.domain.entity.TransactionPaymentDetail;
import com.james.medpay.features.transaction.domain.entity.contract.ITransactionPaymentDetail;
import com.james.medpay.features.transaction.domain.repository.contract.ITransactionPaymentDetailRepository;
import com.james.medpay.features.transaction.domain.repository.dataRepository.ITransactionEntityDataRepository;
import com.james.medpay.features.transaction.domain.repository.dataRepository.ITransactionPaymentDetailDataRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RepositoryImpl
@RequiredArgsConstructor
public class TransactionPaymentDetailRepositoryImpl implements ITransactionPaymentDetailRepository {

	private final ITransactionEntityDataRepository dataRepository;
	private final ITransactionPaymentDetailDataRepository paymentDetailDataRepository;


	@Override
	public ITransactionPaymentDetail createPaymentDetail( TransactionPaymentDetail detail ) {
		return this.paymentDetailDataRepository.save( detail );
	}

	@Override
	public Optional<ITransactionPaymentDetail> getByTransactionId( Long transaction ) {
		Optional<TransactionEntity> optional = this._findByIdFromDataRepository( transaction );
		return optional.map( TransactionEntity::getPaymentDetail );
	}

	@Override
	public Optional<ITransactionPaymentDetail> getByTransactionReference( String reference ) {
		Optional<TransactionEntity> optional = this._findByReferenceFromDataRepository( reference );
		return optional.map( TransactionEntity::getPaymentDetail );
	}

	private Optional<TransactionEntity> _findByIdFromDataRepository( Long id ) {
		return this.dataRepository.findById( id );
	}

	private Optional<TransactionEntity> _findByReferenceFromDataRepository( String reference ) {
		return this.dataRepository.findByReferenceEquals( reference );
	}
}
