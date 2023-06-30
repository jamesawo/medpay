
package com.james.medpay.features.hospital.domain.usecase.hospitalServices;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.hospital.domain.entity.HospitalService;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalServiceRepository;
import java.util.List;

import lombok.RequiredArgsConstructor;


@UseCase
@RequiredArgsConstructor
public class HospitalServiceGetAllByRevenueHeadUsecase implements UsecaseContract< List< HospitalService>, Long> {

	private final IHospitalServiceRepository repository;

	public List< HospitalService > execute(Long revenueHeadId) {
		return repository.getAllServiceInRevenueHead(revenueHeadId);
	}
}
