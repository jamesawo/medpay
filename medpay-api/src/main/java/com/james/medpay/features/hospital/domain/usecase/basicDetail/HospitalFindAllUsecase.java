
package com.james.medpay.features.hospital.domain.usecase.basicDetail;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.core.params.EmptyParam;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalBasicDetailRepository;

import java.util.List;

@UseCase
public class HospitalFindAllUsecase implements UsecaseContract<List<HospitalEntity>, EmptyParam> {

	private final IHospitalBasicDetailRepository repository;

	public HospitalFindAllUsecase( IHospitalBasicDetailRepository repositoryImpl ) {
		this.repository = repositoryImpl;
	}

	@Override
	public List<HospitalEntity> execute( EmptyParam empty ) {
		return repository.getAllHospitals();
	}
}
