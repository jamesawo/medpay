
package com.james.medpay.features.hospital.domain.usecase.revenueHead;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.hospital.domain.entity.HospitalRevenueHead;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalRevenueHeadRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class HospitalRevenueHeadGetByIdUsecase implements UsecaseContract<Optional<HospitalRevenueHead>, Long> {

	private final IHospitalRevenueHeadRepository repository;

	@Override
	public Optional<HospitalRevenueHead> execute( Long revenueHeadId) {
		return repository.getRevenueHeadById(revenueHeadId);
	}
}
