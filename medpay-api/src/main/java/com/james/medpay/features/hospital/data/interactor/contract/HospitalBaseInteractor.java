package com.james.medpay.features.hospital.data.interactor.contract;

import com.james.medpay.core.params.Param;
import com.james.medpay.features.hospital.data.request.*;
import com.james.medpay.features.hospital.data.request.searchRequest.HospitalSearchRequest;
import com.james.medpay.features.hospital.domain.entity.HospitalService;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalActiveStatusEnum;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalEnvironmentEnum;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

// is extended by multiple other implementation
public abstract class HospitalBaseInteractor {

	// todo:: refactor this entire interactor, split sections into smaller and more maintainable interactions.

	// api configuration usecase interaction
	public ResponseEntity<?> createApiConfigurationUsecase( HospitalApiConfigurationRequest request, Long hospitalId ) {
		return null;
	}

	public ResponseEntity<?> getApiConfigurationUsecase( Long hospitalId ) {
		return null;
	}

	public ResponseEntity<?> updateActiveEnvironmentUsecase( Long hospitalId, HospitalEnvironmentEnum environmentEnum ) {
		return null;
	}

	public ResponseEntity<?> updateApiConfigurationUsecase( Long hospitalId, HospitalApiConfigurationRequest request ) {
		return null;
	}

	// basic details
	public ResponseEntity<?> createHospitalUsecase( HospitalRequest request ) {
		return null;
	}

	public ResponseEntity<Optional<HospitalRequest>> getHospitalDetail( Long hospitalId, boolean trim ) {
		return null;
	}

	public ResponseEntity<?> getHospitalBasicDetailUsecase( Long hospitalId ) {
		return null;
	}

	public ResponseEntity<Optional<HospitalRequest>> getHospitalCompleteDetailsUsecase( Long hospitalId ) {
		return null;
	}

	public ResponseEntity<?> updateBasicDetailUsecase( Long hospitalId, HospitalBasicDetailRequest request ) {
		return null;
	}

	public ResponseEntity<?> getAllHospitalsUsecase( boolean trim ) {
		return null;
	}

	public ResponseEntity<List<HospitalRequest>> searchHospitals( HospitalSearchRequest request, boolean trim) {return  null;};

	// hospital service
	public ResponseEntity<?> accessCreateServiceUseCase( Param<HospitalService> params ) {
		return null;
	}

	public ResponseEntity<?> accessUpdateServiceStatusUsecase( Param<HospitalActiveStatusEnum> params ) {
		return null;
	}


	public ResponseEntity<List<HospitalRequest>> searchHospitals( String searchTerm, boolean trim ){return null;};

	public ResponseEntity<?> updatePartialDetail( Long id, HospitalPartialDetailRequest request ){return null;}


}
