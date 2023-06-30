
package com.james.medpay.features.hospital.presenter;

import com.james.medpay.features.hospital.data.interactor.implementation.HospitalApiConfigurationInteractor;
import com.james.medpay.features.hospital.data.request.HospitalApiConfigurationRequest;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalEnvironmentEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Optional;

import static com.james.medpay.core.constant.Routes.API_PREFIX;


@RestController
@RequestMapping( value = API_PREFIX + "/hospital-api" )
public class HospitalApiConfigurationEntryPoint {

	private final HospitalApiConfigurationInteractor interactor;

	public HospitalApiConfigurationEntryPoint(
			HospitalApiConfigurationInteractor interactor
	) {
		this.interactor = interactor;
	}

	@GetMapping( value = "/{hospitalId}" )
	public ResponseEntity<?> getHospitalApiConfiguration( @PathVariable Long hospitalId ) {
		return interactor.getApiConfigurationUsecase( hospitalId );
	}

	@PutMapping( value = "/update/{hospitalId}" )
	public ResponseEntity<Optional<HospitalApiConfigurationRequest>> updateHospitalApiConfiguration(
			@PathVariable Long hospitalId,
			@Valid @RequestBody HospitalApiConfigurationRequest request
	) {
		return interactor.updateApiConfigurationUsecase( hospitalId, request );
	}

	@PatchMapping( "/active/{hospitalId}" )
	public ResponseEntity<?> updateHospitalApiActiveEnvironment(
			@RequestParam( value = "environment" ) HospitalEnvironmentEnum environment,
			@PathVariable( value = "hospitalId" ) Long hospitalId
	) {
		return interactor.updateActiveEnvironmentUsecase( hospitalId, environment );
	}

}
