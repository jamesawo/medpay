
package com.james.medpay.features.hospital.domain.usecase.revenueHead;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.hospital.domain.entity.HospitalRevenueHead;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalRevenueHeadRepository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class HospitalRevenueHeadCreateUsecase implements UsecaseContract<Optional<HospitalRevenueHead>, Param<HospitalRevenueHead>> {

	private final IHospitalRevenueHeadRepository repository;

	@Override
	public Optional<HospitalRevenueHead> execute( Param<HospitalRevenueHead> params ) {
		return repository.createHospitalRevenueHeadInServiceGroup( params );
	}
}
