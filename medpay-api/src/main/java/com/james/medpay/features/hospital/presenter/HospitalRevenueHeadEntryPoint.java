
package com.james.medpay.features.hospital.presenter;

import com.james.medpay.features.hospital.data.interactor.implementation.HospitalRevenueHeadInteractor;
import com.james.medpay.features.hospital.data.request.HospitalRevenueHeadRequest;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.james.medpay.core.constant.Routes.API_PREFIX;


@RestController
@RequestMapping( value = API_PREFIX + "/hospital-revenue-head" )
@RequiredArgsConstructor
public class HospitalRevenueHeadEntryPoint {
	private final HospitalRevenueHeadInteractor interactor;

	@PostMapping( value = "/create/{groupId}" )
	public ResponseEntity< Optional< HospitalRevenueHeadRequest > > create(@PathVariable( value = "groupId" ) Long groupId, @Valid @RequestBody HospitalRevenueHeadRequest request) {
		return this.interactor.createRevenueHead(groupId, request);
	}

	@PatchMapping( value = "/status/{revenueHeadId}" )
	public ResponseEntity< Optional< Boolean > > create(@PathVariable( value = "revenueHeadId" ) Long revenueHeadId, @RequestParam( value = "status" ) boolean status) {
		return this.interactor.updateStatus(revenueHeadId, status);
	}

	@GetMapping( value = "/find-in-service-group/{serviceGroupId}" )
	public ResponseEntity< List< HospitalRevenueHeadRequest > > getByServiceGroup(@PathVariable( value = "serviceGroupId" ) Long serviceGroupId) {
		return this.interactor.getByServiceGroup(serviceGroupId);
	}

	@PostMapping( value = "/update" )
	public ResponseEntity< Optional< HospitalRevenueHeadRequest > > update(@Valid @RequestBody HospitalRevenueHeadRequest request) {
		return this.interactor.updateRevenueHead(request);
	}
}
