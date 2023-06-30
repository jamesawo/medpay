
package com.james.medpay.features.hospital.domain.usecase.serviceGroup;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.hospital.domain.entity.HospitalServiceGroup;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalServiceGroupRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class HospitalUpdateServiceGroupUsecase implements UsecaseContract<Optional<HospitalServiceGroup>, Param<HospitalServiceGroup>> {

	private final IHospitalServiceGroupRepository repository;

	@Override
	public Optional<HospitalServiceGroup> execute(
			Param<HospitalServiceGroup> param
	) {
		return repository.updateServiceGroup( param.getId(), param.getModel() );
	}
}
