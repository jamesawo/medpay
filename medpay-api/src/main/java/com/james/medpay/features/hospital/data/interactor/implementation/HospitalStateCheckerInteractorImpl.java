package com.james.medpay.features.hospital.data.interactor.implementation;

import com.james.medpay.core.annotation.RepositoryImpl;
import com.james.medpay.features.hospital.data.interactor.contract.HospitalStateCheckerInteractor;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalStateCheckerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

@RepositoryImpl
@RequiredArgsConstructor
public class HospitalStateCheckerInteractorImpl implements HospitalStateCheckerInteractor {

	private final IHospitalStateCheckerRepository checkerRepository;

	@Override
	public ResponseEntity<Optional<Boolean>> isHospitalConfigured( Long hospitalId ) {
		Optional<Boolean> optional = this.checkerRepository.isHospitalConfigured( hospitalId );
		return ResponseEntity.ok().body( optional );
	}

	@Override
	public ResponseEntity<Optional<Map<String, Object>>> runHospitalStateChecker( Long hospitalId ) {
		Optional<Map<String, Object>> optional = this.checkerRepository.runHospitalStateChecker( hospitalId );
		return ResponseEntity.ok().body( optional );
	}
}
