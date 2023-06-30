package com.james.medpay.features.hospital.data.interactor.contract;

import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

public interface HospitalStateCheckerInteractor {
	ResponseEntity<Optional<Boolean>> isHospitalConfigured( Long hospitalId );

	ResponseEntity<Optional<Map<String, Object>>> runHospitalStateChecker( Long hospitalId );
}
