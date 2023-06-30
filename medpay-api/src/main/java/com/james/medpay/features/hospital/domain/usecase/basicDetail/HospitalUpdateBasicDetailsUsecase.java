
package com.james.medpay.features.hospital.domain.usecase.basicDetail;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.hospital.domain.entity.HospitalDetail;
import com.james.medpay.core.params.Param;

import com.james.medpay.features.hospital.domain.repository.contract.IHospitalBasicDetailRepository;

import java.util.Optional;

@UseCase
public class HospitalUpdateBasicDetailsUsecase implements UsecaseContract<Optional<HospitalDetail>,
		Param<HospitalDetail>> {

	private final IHospitalBasicDetailRepository repository;

	public HospitalUpdateBasicDetailsUsecase( IHospitalBasicDetailRepository repositoryImpl ) {
		this.repository = repositoryImpl;
	}


	@Override
	public Optional<HospitalDetail> execute( Param<HospitalDetail> params ) {
		return repository.updateHospitalDetails( params );
	}
}
