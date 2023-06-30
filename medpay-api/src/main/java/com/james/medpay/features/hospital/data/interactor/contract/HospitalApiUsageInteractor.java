package com.james.medpay.features.hospital.data.interactor.contract;

import com.james.medpay.features.hospital.domain.entity.partial.HospitalBill;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface HospitalApiUsageInteractor {

	ResponseEntity<Optional<HospitalBill>> apiBillLookup( Long hospitalId, String billNumber );

	ResponseEntity<Optional<Boolean>> apiSendPaymentSuccessNotification( Long hospitalId, String billNumber );
	
	ResponseEntity<Optional<Boolean>> apiCheckPaymentStatus( Long hospitalId, String billNumber );

	ResponseEntity<Optional<Boolean>> apiCheckIsConnectionAlive( Long hospitalId );

}
