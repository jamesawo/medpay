package com.james.medpay.features.hospital.domain.repository.contract;

import com.james.medpay.features.hospital.domain.entity.HospitalServiceGroup;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;

public interface IHospitalServiceGroupRepository {

	Optional<HospitalServiceGroup> updateServiceGroup( Long hospitalId, HospitalServiceGroup serviceGroup);

	List<HospitalServiceGroup> searchByCriteria(Specification<HospitalServiceGroup> specification);

	List<HospitalServiceGroup> getByHospital(Long hospitalId);
}
