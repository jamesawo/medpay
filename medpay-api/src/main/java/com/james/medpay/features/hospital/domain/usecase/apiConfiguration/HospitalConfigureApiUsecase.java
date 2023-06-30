
package com.james.medpay.features.hospital.domain.usecase.apiConfiguration;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.hospital.domain.entity.HospitalApiConfiguration;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalApiConfigRepository;

import java.util.Optional;


@UseCase
public class HospitalConfigureApiUsecase implements UsecaseContract<Optional<HospitalApiConfiguration>, Param<HospitalApiConfiguration>> {

	private final IHospitalApiConfigRepository repository;

	public HospitalConfigureApiUsecase( IHospitalApiConfigRepository implementation ) {
		this.repository = implementation;
	}


	@Override
	public Optional<HospitalApiConfiguration> execute( Param<HospitalApiConfiguration> params ) {
		return repository.updateHospitalApiConfiguration( params );
	}
}
