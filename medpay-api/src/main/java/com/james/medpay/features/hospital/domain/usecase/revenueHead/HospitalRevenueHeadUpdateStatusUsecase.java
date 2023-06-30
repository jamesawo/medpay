
package com.james.medpay.features.hospital.domain.usecase.revenueHead;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalRevenueHeadRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class HospitalRevenueHeadUpdateStatusUsecase implements UsecaseContract<Optional<Boolean>, Param<Boolean>> {

	private final IHospitalRevenueHeadRepository repository;

	@Override
	public Optional< Boolean > execute(Param< Boolean > param) {
		return repository.updateRevenueHeadStatus(param.getId(), param.getModel());
	}
}
