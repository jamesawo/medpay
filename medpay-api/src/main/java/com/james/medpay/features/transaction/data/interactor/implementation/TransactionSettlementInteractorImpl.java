package com.james.medpay.features.transaction.data.interactor.implementation;

import com.james.medpay.core.annotation.Interactor;
import com.james.medpay.core.contract.DataMapper;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.transaction.data.interactor.contract.TransactionSettlementInteractor;
import com.james.medpay.features.transaction.data.request.TransactionSettlementRequest;
import com.james.medpay.features.transaction.domain.entity.TransactionSettlement;
import com.james.medpay.features.transaction.domain.entity.contract.ITransactionSettlement;
import com.james.medpay.features.transaction.domain.usecase.settlement.TransactionGetSettlementByTrIdUsecase;
import com.james.medpay.features.transaction.domain.usecase.settlement.TransactionGetSettlementByTrReferenceUsecase;
import com.james.medpay.features.transaction.domain.usecase.settlement.TransactionUpdateSettlementByTrIdUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@Interactor
@RequiredArgsConstructor
public class TransactionSettlementInteractorImpl implements TransactionSettlementInteractor, DataMapper<ITransactionSettlement, TransactionSettlementRequest> {

	private final TransactionUpdateSettlementByTrIdUsecase updateSettlementByTrIdUsecase;
	private final TransactionGetSettlementByTrReferenceUsecase getSettlementByTrReferenceUsecase;
	private final TransactionGetSettlementByTrIdUsecase getSettlementByTrIdUsecase;


	@Override
	public ResponseEntity<Optional<TransactionSettlementRequest>> getByTransactionId(
			Long id
	) {
		Optional<ITransactionSettlement> optional = getSettlementByTrIdUsecase.execute( id );
		return ResponseEntity.ok().body( optional.map( this::toRequest ) );
	}

	@Override
	public ResponseEntity<Optional<TransactionSettlementRequest>> getByTransactionReference( String reference ) {
		Optional<ITransactionSettlement> optional = getSettlementByTrReferenceUsecase.execute( reference );
		return ResponseEntity.ok().body( optional.map( this::toRequest ) );
	}

	@Override
	public ResponseEntity<Optional<TransactionSettlementRequest>> updateByTransactionId(
			Long transactionId, ITransactionSettlement settlement
	) {
		Optional<ITransactionSettlement> optional = this.updateSettlementByTrIdUsecase.execute(
				new Param<>( transactionId, settlement ) );
		return ResponseEntity.ok().body( optional.map( this::toRequest ) );
	}

	@Override
	public ITransactionSettlement toEntity(
			TransactionSettlementRequest request
	) {
		return TransactionSettlementRequest.toEntity( request );
	}

	@Override
	public TransactionSettlementRequest toRequest(
			ITransactionSettlement settlement
	) {
		TransactionSettlement entity = ( TransactionSettlement ) settlement;
		return TransactionSettlementRequest.toRequest( entity );
	}
}
