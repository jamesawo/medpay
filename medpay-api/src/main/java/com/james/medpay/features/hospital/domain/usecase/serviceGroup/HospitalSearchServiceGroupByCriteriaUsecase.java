
package com.james.medpay.features.hospital.domain.usecase.serviceGroup;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.hospital.domain.entity.HospitalServiceGroup;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalServiceGroupRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@UseCase
@RequiredArgsConstructor
public class HospitalSearchServiceGroupByCriteriaUsecase implements UsecaseContract< List< HospitalServiceGroup >, Specification<HospitalServiceGroup> > {

	private final IHospitalServiceGroupRepository serviceGroupRepository;

	@Override
	public List< HospitalServiceGroup > execute(Specification< HospitalServiceGroup > specification) {
		return this.serviceGroupRepository.searchByCriteria(specification);
	}
}
