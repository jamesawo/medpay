
package com.james.medpay.features.hospital.domain.usecase.hospitalServices;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.hospital.domain.entity.HospitalService;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalServiceRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;


@UseCase
@RequiredArgsConstructor
public class HospitalServiceUpdateUsecase implements UsecaseContract< Optional< Boolean>, HospitalService> {

	private final IHospitalServiceRepository repository;

	@Override
	public Optional< Boolean > execute(HospitalService service) {
		return repository.updateService(service);
	}
}
