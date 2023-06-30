
package com.james.medpay.features.hospital.domain.usecase.hospitalServices;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.hospital.domain.entity.HospitalService;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalServiceRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;


@UseCase
@RequiredArgsConstructor
public class HospitalServiceSearchByTitleFilterByHospitalUsecase implements UsecaseContract<List<HospitalService>, Param<String>> {

	private final IHospitalServiceRepository repository;


	@Override
	public List<HospitalService> execute( Param<String> param ) {
		return repository.searchWithTitleAndFilterByHospital( param.getId(), param.getModel() );
	}
}
