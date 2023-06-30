
package com.james.medpay.features.hospital.presenter;

import com.james.medpay.features.hospital.data.interactor.implementation.HospitalServiceGroupInteractor;
import com.james.medpay.features.hospital.data.request.HospitalServiceGroupRequest;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.james.medpay.core.constant.Routes.API_PREFIX;


@RestController
@RequestMapping( value = API_PREFIX + "/hospital-service-group" )
@RequiredArgsConstructor
public class HospitalServiceGroupEntryPoint {

	private final HospitalServiceGroupInteractor interactor;

	@PostMapping( value = "/create" )
	public ResponseEntity< Optional< HospitalServiceGroupRequest > > update(@Valid @RequestBody HospitalServiceGroupRequest request) {
		return this.interactor.updateHospitalServiceGroup(request);
	}

	@GetMapping(value = "/get-by-hospital/{hospitalId}")
	public ResponseEntity< List< HospitalServiceGroupRequest > > getByHospital( @PathVariable Long hospitalId ) {
		return this.interactor.getServiceGroupByHospitalId( hospitalId );
	}
}
