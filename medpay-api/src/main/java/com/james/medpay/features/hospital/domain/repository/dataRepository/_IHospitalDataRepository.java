package com.james.medpay.features.hospital.domain.repository.dataRepository;

import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface _IHospitalDataRepository extends JpaRepository<HospitalEntity, Long>,
                                                  JpaSpecificationExecutor<HospitalEntity> {

	List<HospitalEntity> findAll( Specification<HospitalEntity> spec );

}
