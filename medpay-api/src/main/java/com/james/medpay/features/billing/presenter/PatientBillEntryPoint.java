package com.james.medpay.features.billing.presenter;

import com.james.medpay.features.billing.data.interactor.PatientBillInteractor;
import com.james.medpay.features.billing.data.request.PatientBillRequest;
import com.james.medpay.features.billing.data.request.PatientRequest;
import com.james.medpay.features.billing.domain.usecase.IPatientBillUsecase;
import com.james.medpay.features.billing.domain.usecase.IPatientUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.james.medpay.core.constant.Routes.API_PREFIX;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping( value = API_PREFIX + "/billing" )
@RequiredArgsConstructor
public class PatientBillEntryPoint {
	private final PatientBillInteractor interactor;

	@PostMapping(value = "/register")
	public ResponseEntity<PatientBillRequest> register(@RequestBody PatientBillRequest request){
		return this.interactor.register(request);
	}

	@GetMapping(value = "/search")
	public ResponseEntity<List<PatientBillRequest>> search(@RequestParam(value = "term") String term){
		return this.interactor.search(term);
	}
}
