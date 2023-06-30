
package com.james.medpay.features.hospital.presenter;

import com.james.medpay.features.hospital.data.interactor.implementation.HospitalBasicDetailInteractor;
import com.james.medpay.features.hospital.data.request.HospitalBasicDetailRequest;
import com.james.medpay.features.hospital.data.request.HospitalPartialDetailRequest;
import com.james.medpay.features.hospital.data.request.HospitalRequest;
import com.james.medpay.features.hospital.data.request.searchRequest.HospitalSearchRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.james.medpay.core.constant.Routes.API_PREFIX;


@RestController
@RequestMapping( value = API_PREFIX + "/hospital" )
public class HospitalBasicDetailEntryPoint {

	private final HospitalBasicDetailInteractor interactor;

	public HospitalBasicDetailEntryPoint( HospitalBasicDetailInteractor interactor ) {
		this.interactor = interactor;
	}

	@PostMapping( value = "/create" )
	public ResponseEntity<HospitalRequest> createHospital( @Valid @RequestBody HospitalRequest request ) {
		return interactor.createHospitalUsecase(request);
	}


	@PostMapping( value = "/search-by-payload" )
	public ResponseEntity<List<HospitalRequest>> searchHospital(
			@Valid @RequestBody HospitalSearchRequest request,
			@RequestParam( value = "trim", required = false, defaultValue = "true") boolean trim
	) {
		return interactor.searchHospitals(request, trim);
	}

	@GetMapping( value = "/search-by-name" )
	public ResponseEntity<List<HospitalRequest>> searchHospitalByName(
			@RequestParam( value = "search" ) String search,
			@RequestParam( value = "trim", required = false, defaultValue = "true") boolean trim
	) {
		return interactor.searchHospitals(search, trim);
	}

	@GetMapping( value = "/{hospitalId}" )
	public ResponseEntity<?> getHospitalDetails(
			@PathVariable String hospitalId,
			@RequestParam( value = "trim", required = false, defaultValue = "true" ) boolean trim
	) throws NumberFormatException {
		return interactor.getHospitalDetail( Long.valueOf( hospitalId ), trim );
	}

	@GetMapping( value = "/all" )
	public ResponseEntity<List<HospitalRequest>> getAllHospitals(
			@RequestParam( value = "trim", required = false, defaultValue = "true" ) boolean trim
	) {
		return interactor.getAllHospitalsUsecase( trim );
	}

	@PutMapping( value = "/update/{hospitalId}" )
	public ResponseEntity<?> updateHospitalBasicDetails(
			@PathVariable String hospitalId,
			@Valid @RequestBody HospitalBasicDetailRequest request
	) throws NumberFormatException {
		return interactor.updateBasicDetailUsecase( Long.valueOf( hospitalId ), request );
	}

	@PutMapping( value = "/update/collection-mode/{hospitalId}" )
	public ResponseEntity<?> updateHospitalPartialDetails(
			@PathVariable String hospitalId,
			@Valid @RequestBody HospitalPartialDetailRequest request
			) throws NumberFormatException {
		return interactor.updatePartialDetail( Long.valueOf( hospitalId ), request );
	}

}
