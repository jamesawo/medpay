package com.james.medpay.features.hospital.data.interactor.implementation;

import com.james.medpay.core.annotation.Interactor;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.hospital.data.interactor.contract.serviceGroup.IHospitalServiceInteractor;
import com.james.medpay.features.hospital.data.request.HospitalServiceRequest;
import com.james.medpay.features.hospital.domain.entity.HospitalService;
import com.james.medpay.features.hospital.domain.usecase.hospitalServices.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@Interactor
@RequiredArgsConstructor
public class HospitalServiceInteractor implements IHospitalServiceInteractor {

	private final HospitalServiceCreateUsecase createUsecase;
	private final HospitalServiceGetAllByRevenueHeadUsecase getAllByRevenueHeadUsecase;
	private final HospitalServiceUpdateStatusUsecase updateStatusUsecase;
	private final HospitalServiceUpdateUsecase updateUsecase;
	private final HospitalServiceSearchByTitleFilterByHospitalUsecase searchAndFilterByHospitalUsecase;


	@Override
	public ResponseEntity<Optional<HospitalServiceRequest>> create( Long revenueHeadId, HospitalServiceRequest request ) {
		Optional<HospitalService> optional = this.createUsecase.execute( new Param<>( revenueHeadId, HospitalServiceRequest.toEntity( request ) ) );
		return ok().body( optional.map( HospitalServiceRequest::toRequest ) );
	}

	@Override
	public ResponseEntity<Optional<Boolean>> update( HospitalServiceRequest request ) {
		return ok().body( this.updateUsecase.execute( HospitalServiceRequest.toEntity( request ) ) );
	}

	@Override
	public ResponseEntity<List<HospitalServiceRequest>> getAllByRevenueHead( Long revHeadId ) {
		List<HospitalService> list = this.getAllByRevenueHeadUsecase.execute( revHeadId );
		return ok().body( list.stream().map( HospitalServiceRequest::toRequest ).collect( Collectors.toList() ) );
	}

	@Override
	public ResponseEntity<Optional<Boolean>> updateStatus( Long serviceId, boolean status ) {
		Optional<Boolean> optional = this.updateStatusUsecase.execute( new Param<>( serviceId, status ) );
		return ok().body( optional );
	}

	@Override
	public ResponseEntity<List<HospitalServiceRequest>> searchByTitleAndHospital( Long hospitalId, String title ) {
		List<HospitalService> list = this.searchAndFilterByHospitalUsecase.execute( new Param<>( hospitalId, title ) );
		return ok().body( list.stream().map( HospitalServiceRequest::toRequest ).collect( Collectors.toList() ) );
	}
}
