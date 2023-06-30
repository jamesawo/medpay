package com.james.medpay.features.hospital.data.interactor.implementation;

import com.james.medpay.core.annotation.Interactor;
import com.james.medpay.core.contract.DataMapper;
import com.james.medpay.core.exception.InvalidRequestException;
import com.james.medpay.core.params.EmptyParam;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.hospital.data.interactor.contract.HospitalBaseInteractor;
import com.james.medpay.features.hospital.data.request.*;
import com.james.medpay.features.hospital.data.request.searchRequest.HospitalSearchRequest;
import com.james.medpay.features.hospital.data.request.specification.HospitalSearchSpecification;
import com.james.medpay.features.hospital.domain.entity.HospitalDetail;
import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.features.hospital.domain.usecase.basicDetail.*;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.*;

@Interactor
public class HospitalBasicDetailInteractor extends HospitalBaseInteractor implements DataMapper<HospitalRequest, HospitalEntity> {

	public static final String INVALID_REQUEST = "Error, Please Provide Hospital Basic Details";
	private final HospitalCreateHospitalUsecase createHospitalUsecase;
	private final HospitalGetHospitalBasicDetailUsecase getHospitalBasicDetailUsecase;
	private final HospitalUpdateBasicDetailsUsecase updateBasicDetailsUsecase;
	private final HospitalFindAllUsecase findAllUsecase;
	private final HospitalGetHospitalCompleteDetailUsecase getHospitalCompleteDetailUsecase;
	private final HospitalFindWithSearchRequestUsecase searchRequestUsecase;
	private final HospitalUpdatePartialDetailsUsecase updatePartialDetailsUsecase;

	public HospitalBasicDetailInteractor(
			HospitalCreateHospitalUsecase createHospitalUsecase,
			HospitalGetHospitalBasicDetailUsecase getHospitalBasicDetailUsecase,
			HospitalUpdateBasicDetailsUsecase updateBasicDetailsUsecase,
			HospitalFindAllUsecase findAllUsecase,
			HospitalGetHospitalCompleteDetailUsecase getHospitalCompleteDetailUsecase,
			HospitalFindWithSearchRequestUsecase searchRequestUsecase,
			HospitalUpdatePartialDetailsUsecase updatePartialDetailsUsecase
	) {
		this.createHospitalUsecase = createHospitalUsecase;
		this.getHospitalBasicDetailUsecase = getHospitalBasicDetailUsecase;
		this.updateBasicDetailsUsecase = updateBasicDetailsUsecase;
		this.findAllUsecase = findAllUsecase;
		this.getHospitalCompleteDetailUsecase = getHospitalCompleteDetailUsecase;
		this.searchRequestUsecase = searchRequestUsecase;
		this.updatePartialDetailsUsecase = updatePartialDetailsUsecase;
	}


	@Override
	public ResponseEntity<HospitalRequest> createHospitalUsecase( HospitalRequest request ) {
		HospitalEntity hospital = this.createHospitalUsecase.execute( toRequest( request ) );
		HospitalRequest hospitalRequest = toEntity( hospital );
		return ok().body( hospitalRequest );
	}


	@Override
	public ResponseEntity<Optional<HospitalRequest>> getHospitalDetail( Long hospitalId, boolean trim ) {
		if ( trim ) {
			return this.getHospitalBasicDetailUsecase( hospitalId );
		}
		return getHospitalCompleteDetailsUsecase( hospitalId );
	}


	@Override
	public ResponseEntity<Optional<HospitalRequest>> getHospitalBasicDetailUsecase( Long hospitalId ) {
		Optional<HospitalEntity> optional = this.getHospitalBasicDetailUsecase.execute( hospitalId );
		Optional<HospitalRequest> request = optional.map( hospital -> toEntity( hospital.trim() ) );
		return ok().body( request );
	}


	@Override
	public ResponseEntity<Optional<HospitalRequest>> getHospitalCompleteDetailsUsecase( Long hospitalId ) {
		Optional<HospitalEntity> optional = this.getHospitalCompleteDetailUsecase.execute( hospitalId );
		Optional<HospitalRequest> request = optional.map( this::toEntity );
		return ok().body( request );
	}


	@Override
	public ResponseEntity<Optional<HospitalBasicDetailRequest>> updateBasicDetailUsecase(
			Long hospitalId, HospitalBasicDetailRequest request ) {
		if ( ObjectUtils.isEmpty( request ) ) {
			_throwInvalidRequestExceptionWithMessage();
		}
		return _callUsecaseToUpdateHospitalBasicDetails( hospitalId, request );
	}

	private void _throwInvalidRequestExceptionWithMessage() throws InvalidRequestException {
		throw new InvalidRequestException( INVALID_REQUEST );
	}

	private ResponseEntity<Optional<HospitalBasicDetailRequest>> _callUsecaseToUpdateHospitalBasicDetails(
			Long hospitalId, HospitalBasicDetailRequest request
	) {
		Param<HospitalDetail> params = new Param<>( hospitalId, HospitalBasicDetailRequest.toEntity( request ) );
		Optional<HospitalDetail> optional = this.updateBasicDetailsUsecase.execute( params );
		return ok().body( optional.map( HospitalBasicDetailRequest::toRequest ) );
	}

	@Override
	public ResponseEntity<List<HospitalRequest>> getAllHospitalsUsecase( boolean trim ) {
		List<HospitalEntity> entities = this.findAllUsecase.execute( new EmptyParam() );
		if ( !entities.isEmpty() ) {
			return ok().body( _mapToListOfHospitalRequest( entities, trim ) );
		}
		return ok().body( new ArrayList<>() );
	}

	@Override
	public ResponseEntity<List<HospitalRequest>> searchHospitals( HospitalSearchRequest request, boolean trim) {
		List<HospitalEntity> list = this.searchRequestUsecase.execute(HospitalSearchSpecification.byRequestPayload(request));
		List<HospitalRequest> requestList = list.stream().map( entity -> HospitalRequest.from( entity.trim() ) ).collect(
				Collectors.toList() );
		return ok().body(requestList);
	}

	@Override
	public ResponseEntity<List<HospitalRequest>> searchHospitals( String searchTerm, boolean trim ) {
		List<HospitalEntity> list = this.searchRequestUsecase.execute( HospitalSearchSpecification.byName(searchTerm) );
		List<HospitalRequest> requestList = list.stream().map(
				entity -> HospitalRequest.from( trim ? entity.trim() : entity ) ).collect(
				Collectors.toList() );
		return ok().body(requestList);
	}

	@Override
	public ResponseEntity<Optional<HospitalPartialDetailRequest>> updatePartialDetail(Long id, HospitalPartialDetailRequest request) {
		Optional<HospitalEntity> result = this.updatePartialDetailsUsecase.execute(
				new Param<>( id, HospitalPartialDetailRequest.toEntity( request ) ) );
		return ok().body(result.map( HospitalPartialDetailRequest::toRequest ));
	}

	public List<HospitalRequest> _mapToListOfHospitalRequest( List<HospitalEntity> entities, boolean trim ) {
		return entities.stream().map( entity -> HospitalRequest.from( trim ? entity.trim() : entity ) ).collect(
				Collectors.toList() );
	}

	@Override
	public HospitalRequest toEntity( HospitalEntity hospital ) {
		return HospitalRequest.from( hospital );
	}

	@Override
	public HospitalEntity toRequest( HospitalRequest request ) {
		return HospitalRequest.to( request );
	}
}
