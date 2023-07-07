package com.james.medpay.features.billing.presenter;

import com.james.medpay.features.billing.data.interactor.PatientInteractor;
import com.james.medpay.features.billing.data.request.PatientRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.james.medpay.core.constant.Routes.API_PREFIX;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping( value = API_PREFIX + "/billing/patient" )
@RequiredArgsConstructor
public class PatientEntryPoint {
	private final PatientInteractor interactor;

	@PostMapping(value = "/register")
	public ResponseEntity<PatientRequest> register(@RequestBody PatientRequest patientRequest){
		return this.interactor.register(patientRequest);
	}

	@GetMapping(value = "/search")
	public ResponseEntity<List<PatientRequest>> search(@RequestParam(value = "term") String term){
		return this.interactor.search(term);
	}
}
