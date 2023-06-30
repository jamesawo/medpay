package com.james.medpay.features.hospital.domain.repository.dataRepository;

import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.features.hospital.domain.entity.HospitalServiceGroup;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface _IHospitalServiceGroupDataRepository extends JpaRepository< HospitalServiceGroup, Long>, JpaSpecificationExecutor<HospitalServiceGroup> {
	List<HospitalServiceGroup> findAll(Specification<HospitalServiceGroup> specification);
	List<HospitalServiceGroup> findByHospital( HospitalEntity hospital );
}
