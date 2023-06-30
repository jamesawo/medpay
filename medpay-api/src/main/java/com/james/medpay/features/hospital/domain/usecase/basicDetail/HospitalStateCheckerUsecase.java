
package com.james.medpay.features.hospital.domain.usecase.basicDetail;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalStateCheckerRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * Perform a series of checks to determine the state(readiness) of hospital.
 * <p>
 * will check isEnabled property,
 * will check collectionModelEnum
 * <p>
 * will check hasHospitalSoftware,
 * <p>
 * if hasHospitalSoftware is true:
 * will check apiConfiguration
 * <p>
 * if hasHospitalSoftware is false
 * will check serviceGroup,
 * will check atLeast 1 revenueHead,
 * will check atLeast 1 service
 */
@UseCase
@RequiredArgsConstructor
public class HospitalStateCheckerUsecase implements UsecaseContract<Optional<Boolean>, Long> {

	private final IHospitalStateCheckerRepository stateCheckerRepository;

	@Override
	public Optional<Boolean> execute( Long hospitalId ) {
		return stateCheckerRepository.isHospitalConfigured( hospitalId );
	}
}
