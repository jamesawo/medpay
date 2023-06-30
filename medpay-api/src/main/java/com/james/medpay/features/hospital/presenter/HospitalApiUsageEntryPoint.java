
package com.james.medpay.features.hospital.presenter;

import com.james.medpay.features.hospital.data.interactor.implementation.HospitalApiUsageInteractorImpl;
import com.james.medpay.features.hospital.domain.entity.partial.HospitalBill;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.james.medpay.core.constant.Routes.API_PREFIX;

@RestController
@RequestMapping( value = API_PREFIX + "/hospital-api-usage" )
@RequiredArgsConstructor
public class HospitalApiUsageEntryPoint {

	private final HospitalApiUsageInteractorImpl interactor;

	@GetMapping( value = "bill" )
	public ResponseEntity<Optional<HospitalBill>> searchBillDetails(
			@RequestParam( value = "billNumber" ) String billNumber,
			@RequestParam( value = "hospitalId" ) Long hospitalId
	) {
		return interactor.apiBillLookup( hospitalId, billNumber );
	}

	// todo:: remove this endpoint, use create transaction instead
	@PostMapping( value = "pay" )
	public ResponseEntity<Optional<Boolean>> payBill(
			@RequestParam( value = "billNumber" ) String billNumber,
			@RequestParam( value = "hospitalId" ) Long hospitalId
	) {
		return interactor.apiSendPaymentSuccessNotification( hospitalId, billNumber );
	}

	
	@GetMapping( value = "status" )
	public ResponseEntity<Optional<Boolean>> getBillStatus(
			@RequestParam( value = "billNumber" ) String billNumber,
			@RequestParam( value = "hospitalId" ) Long hospitalId
	) {
		return interactor.apiCheckPaymentStatus( hospitalId, billNumber );
	}

	@PostMapping( value = "connection" )
	public ResponseEntity<Optional<Boolean>> getApiConnectionStatus(
			@RequestParam( value = "billNumber" ) String billNumber,
			@RequestParam( value = "hospitalId" ) Long hospitalId
	) {
		return interactor.apiCheckIsConnectionAlive( hospitalId );
	}


}
