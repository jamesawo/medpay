
package com.james.medpay.features.hospital.domain.usecase.apiUsage;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.hospital.data.repositoryImp.HospitalApiUsageRepositoryImpl;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class HospitalApiUsageCheckIsConnectionAliveUsecase implements UsecaseContract<Optional<Boolean>, Long> {

	private final HospitalApiUsageRepositoryImpl apiUsageRepository;

	@Override
	public Optional<Boolean> execute( Long hospitalId ) {
		return apiUsageRepository.apiCheckIsConnectionAlive( hospitalId );
	}
}
