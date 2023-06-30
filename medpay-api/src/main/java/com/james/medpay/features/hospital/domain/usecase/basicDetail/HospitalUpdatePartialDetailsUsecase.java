
package com.james.medpay.features.hospital.domain.usecase.basicDetail;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalBasicDetailRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class HospitalUpdatePartialDetailsUsecase implements UsecaseContract<Optional<HospitalEntity>, Param<HospitalEntity>> {
	private final IHospitalBasicDetailRepository repository;

	@Override
	public Optional<HospitalEntity> execute( Param<HospitalEntity> param ) {
		return this.repository.updatePartialDetails( param );
	}
}
