
package com.james.medpay.features.hospital.domain.usecase.revenueHead;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.hospital.domain.entity.HospitalRevenueHead;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalRevenueHeadRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class HospitalRevenueHeadUpdateUsecase implements UsecaseContract<Optional<HospitalRevenueHead>, HospitalRevenueHead> {

	private final IHospitalRevenueHeadRepository repository;

	@Override
	public Optional<HospitalRevenueHead> execute( HospitalRevenueHead revenueHead ) {
		return repository.updateRevenueHead( revenueHead );
	}
}
