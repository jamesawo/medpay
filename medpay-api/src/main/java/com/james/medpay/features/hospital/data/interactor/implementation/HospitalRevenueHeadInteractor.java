package com.james.medpay.features.hospital.data.interactor.implementation;

import com.james.medpay.core.annotation.Interactor;
import com.james.medpay.features.hospital.data.interactor.contract.serviceGroup.IHospitalRevenueHeadInteractor;
import com.james.medpay.features.hospital.data.request.HospitalRevenueHeadRequest;
import com.james.medpay.features.hospital.domain.entity.HospitalRevenueHead;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.hospital.domain.usecase.revenueHead.HospitalRevenueHeadCreateUsecase;
import com.james.medpay.features.hospital.domain.usecase.revenueHead.HospitalRevenueHeadGetAllByServiceGroupUsecase;
import com.james.medpay.features.hospital.domain.usecase.revenueHead.HospitalRevenueHeadUpdateStatusUsecase;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.james.medpay.features.hospital.domain.usecase.revenueHead.HospitalRevenueHeadUpdateUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@Interactor
@RequiredArgsConstructor
public class HospitalRevenueHeadInteractor implements IHospitalRevenueHeadInteractor {

	private final HospitalRevenueHeadCreateUsecase createRevenueHeadUsecase;
	private final HospitalRevenueHeadUpdateStatusUsecase updateStatusUsecase;
	private final HospitalRevenueHeadGetAllByServiceGroupUsecase getAllByServiceGroupUsecase;
	private final HospitalRevenueHeadUpdateUsecase updateUsecase;

	@Override
	public ResponseEntity<Optional<HospitalRevenueHeadRequest>> createRevenueHead( Long serviceGroupId, HospitalRevenueHeadRequest request ) {
		Optional<HospitalRevenueHead> optional = this.createRevenueHeadUsecase.execute(
				new Param<>( serviceGroupId, HospitalRevenueHeadRequest.toEntity( request ) ) );
		return ResponseEntity.ok().body( optional.map( HospitalRevenueHeadRequest::toRequest ) );
	}

	@Override
	public ResponseEntity<Optional<Boolean>> updateStatus( Long revId, boolean status ) {
		return ResponseEntity.ok().body( this.updateStatusUsecase.execute( new Param<>( revId, status ) ) );
	}

	public ResponseEntity<List<HospitalRevenueHeadRequest>> getByServiceGroup( Long serviceGroupId ) {
		List<HospitalRevenueHead> list = this.getAllByServiceGroupUsecase.execute( serviceGroupId );
		return ResponseEntity.ok().body( list.stream().map( HospitalRevenueHeadRequest::toRequest ).collect( Collectors.toList() ) );
	}

	@Override
	public ResponseEntity<Optional<HospitalRevenueHeadRequest>> updateRevenueHead( HospitalRevenueHeadRequest request ) {
		Optional<HospitalRevenueHead> optional = this.updateUsecase.execute( HospitalRevenueHeadRequest.toEntity( request ) );
		return ResponseEntity.ok().body( optional.map( HospitalRevenueHeadRequest::toRequest ) );
	}
}

