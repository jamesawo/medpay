
package com.james.medpay.features.transaction.data.repositoryImpl;

import com.james.medpay.core.annotation.RepositoryImpl;
import com.james.medpay.features.transaction.domain.entity.TransactionEntity;
import com.james.medpay.features.transaction.domain.entity.TransactionSettlement;
import com.james.medpay.features.transaction.domain.entity.contract.ITransactionSettlement;
import com.james.medpay.features.transaction.domain.repository.contract.ITransactionSettlementRepository;
import com.james.medpay.features.transaction.domain.repository.dataRepository.ITransactionEntityDataRepository;
import com.james.medpay.features.transaction.domain.repository.dataRepository.ITransactionSettlementDataRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RepositoryImpl
@RequiredArgsConstructor
public class TransactionSettlementRepositoryImpl implements ITransactionSettlementRepository {

	private final ITransactionEntityDataRepository dataRepository;
	private final ITransactionSettlementDataRepository settlementDataRepository;

	@Override
	public ITransactionSettlement createSettlement( TransactionSettlement settlement ) {
		return this.settlementDataRepository.save( settlement );
	}

	@Override
	public Optional<ITransactionSettlement> getByTransactionId( Long id ) {
		Optional<TransactionEntity> optional = this._findByIdFromDataRepository( id );
		return optional.map( TransactionEntity::getSettlement );
	}

	@Override
	public Optional<ITransactionSettlement> getByTransactionReference( String reference ) {
		Optional<TransactionEntity> optional = this._findByReferenceFromDataRepository( reference );
		return optional.map( TransactionEntity::getSettlement );
	}

	@Override
	public Optional<ITransactionSettlement> updateByTransactionId(
			Long transactionId, ITransactionSettlement settlement
	) {
		Optional<TransactionEntity> optional = this._findByIdFromDataRepository( transactionId );
		return optional.map( transaction -> {
			settlement.setId( transaction.getSettlement().getId() );
			TransactionSettlement newSettlement = ( TransactionSettlement ) settlement;
			return this.settlementDataRepository.save( newSettlement );
		} );
	}

	private Optional<TransactionEntity> _findByIdFromDataRepository( Long id ) {
		return this.dataRepository.findById( id );
	}

	private Optional<TransactionEntity> _findByReferenceFromDataRepository( String reference ) {
		return this.dataRepository.findByReferenceEquals( reference );
	}
}
