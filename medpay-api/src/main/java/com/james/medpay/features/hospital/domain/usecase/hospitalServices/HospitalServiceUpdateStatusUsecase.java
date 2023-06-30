
package com.james.medpay.features.hospital.domain.usecase.hospitalServices;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalServiceRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class HospitalServiceUpdateStatusUsecase implements UsecaseContract< Optional< Boolean >, Param< Boolean > > {

	private final IHospitalServiceRepository repository;

	@Override
	public Optional< Boolean > execute(Param< Boolean > params) {
		return this.repository.updateStatus(params.getId(), params.getModel());
	}
}
