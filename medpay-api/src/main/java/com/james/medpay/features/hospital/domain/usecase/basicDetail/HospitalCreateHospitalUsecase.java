
package com.james.medpay.features.hospital.domain.usecase.basicDetail;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalBasicDetailRepository;

@UseCase
public class HospitalCreateHospitalUsecase implements UsecaseContract<HospitalEntity, HospitalEntity> {

	private final IHospitalBasicDetailRepository repository;

	public HospitalCreateHospitalUsecase( IHospitalBasicDetailRepository repositoryImpl ) {
		this.repository = repositoryImpl;
	}

	@Override
	public HospitalEntity execute(
			HospitalEntity hospital
	) {
		return repository.createHospital( hospital );
	}
}
