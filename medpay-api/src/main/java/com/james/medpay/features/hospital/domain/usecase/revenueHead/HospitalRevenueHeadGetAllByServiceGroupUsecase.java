
package com.james.medpay.features.hospital.domain.usecase.revenueHead;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.hospital.domain.entity.HospitalRevenueHead;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalRevenueHeadRepository;
import java.util.List;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class HospitalRevenueHeadGetAllByServiceGroupUsecase implements UsecaseContract<List<HospitalRevenueHead>, Long> {

	private final IHospitalRevenueHeadRepository repository;

	@Override
	public List<HospitalRevenueHead> execute( Long serviceGroupId) {
		return repository.getAllRevenueHeadsInServiceGroup(serviceGroupId);
	}
}
