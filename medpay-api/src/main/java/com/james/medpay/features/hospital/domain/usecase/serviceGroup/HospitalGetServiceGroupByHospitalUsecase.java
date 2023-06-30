
package com.james.medpay.features.hospital.domain.usecase.serviceGroup;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.hospital.domain.entity.HospitalServiceGroup;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalServiceGroupRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class HospitalGetServiceGroupByHospitalUsecase implements UsecaseContract< List< HospitalServiceGroup >, Long > {
	private final IHospitalServiceGroupRepository repository;

	@Override
	public List< HospitalServiceGroup > execute(Long hospitalId) {
		return repository.getByHospital(hospitalId);
	}
}
