
package com.james.medpay.features.hospital.presenter;

import com.james.medpay.features.hospital.data.interactor.contract.serviceGroup.IHospitalServiceInteractor;
import com.james.medpay.features.hospital.data.request.HospitalServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

import static com.james.medpay.core.constant.Routes.API_PREFIX;


@RestController
@RequestMapping( value = API_PREFIX + "/hospital-service" )
@RequiredArgsConstructor
public class HospitalServiceEntryPoint {

	private final IHospitalServiceInteractor interactor;

	@PostMapping( value = "/create" )
	public ResponseEntity<Optional<HospitalServiceRequest>> create(
			@Valid @RequestBody HospitalServiceRequest request,
			@RequestParam( value = "revenueHead" ) Long revenueHeadId
	) {
		return this.interactor.create( revenueHeadId, request );
	}

	@PostMapping( value = "/update" )
	public ResponseEntity<Optional<Boolean>> update( @Valid @RequestBody HospitalServiceRequest request ) {
		return this.interactor.update( request );
	}

	@GetMapping( value = "/get-by-revenue-head/{revenueHeadId}" )
	public ResponseEntity<List<HospitalServiceRequest>> getAllByRevenueHead( @PathVariable Long revenueHeadId ) {
		return this.interactor.getAllByRevenueHead( revenueHeadId );
	}

	@PatchMapping( value = "/status/{serviceId}" )
	public ResponseEntity<Optional<Boolean>> updateStatus( @PathVariable Long serviceId, @RequestParam( value = "status" ) boolean status ) {
		return this.interactor.updateStatus( serviceId, status );
	}

	@GetMapping(value = "/search-by-title")
	public ResponseEntity<List<HospitalServiceRequest>> searchServiceByTitleAndHospital(
			@RequestParam(value = "title") String title,
			@RequestParam(value = "hospital") Long hospitalId
	){
		return interactor.searchByTitleAndHospital( hospitalId, title );
	}
}
