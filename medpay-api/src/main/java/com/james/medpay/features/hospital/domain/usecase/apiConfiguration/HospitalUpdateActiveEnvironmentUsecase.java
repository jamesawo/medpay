
package com.james.medpay.features.hospital.domain.usecase.apiConfiguration;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalEnvironmentEnum;
import com.james.medpay.core.params.Param;

import com.james.medpay.features.hospital.domain.repository.contract.IHospitalApiConfigRepository;

import java.util.Optional;

@UseCase
public class HospitalUpdateActiveEnvironmentUsecase implements UsecaseContract<Optional<Boolean>, Param<HospitalEnvironmentEnum>> {


	private final IHospitalApiConfigRepository repository;

	public HospitalUpdateActiveEnvironmentUsecase( IHospitalApiConfigRepository implementation ) {
		this.repository = implementation;
	}


	@Override
	public Optional<Boolean> execute( Param<HospitalEnvironmentEnum> params ) {
		return this.repository.updateHospitalApiActiveEnvironment( params );
	}
}
