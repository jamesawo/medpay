
package com.james.medpay.features.hospital.domain.usecase.basicDetail;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalBasicDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class HospitalFindWithSearchRequestUsecase implements UsecaseContract<List<HospitalEntity>, Specification<HospitalEntity>> {

	private final IHospitalBasicDetailRepository repository;

	@Override
	public List<HospitalEntity> execute(
			Specification<HospitalEntity> spec
	) {
		return this.repository.getHospitalsWithSearchRequest( spec );
	}
}
