package com.james.medpay.features.hospital.domain.usecase.apiConfiguration;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.hospital.domain.entity.HospitalApiConfiguration;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalApiConfigRepository;

import java.util.Optional;

@UseCase
public class HospitalGetApiConfigurationUsecase implements UsecaseContract<Optional<HospitalApiConfiguration>, Long> {

	private final IHospitalApiConfigRepository repository;

	public HospitalGetApiConfigurationUsecase( IHospitalApiConfigRepository implementation ) {
		this.repository = implementation;
	}


	@Override
	public Optional<HospitalApiConfiguration> execute( Long hospitalId ) {
		return repository.getHospitalApiConfiguration( hospitalId );
	}
}
