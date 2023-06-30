package com.james.medpay.features.hospital.domain.repository.dataRepository;

import com.james.medpay.features.hospital.domain.entity.HospitalRevenueHead;
import com.james.medpay.features.hospital.domain.entity.HospitalServiceGroup;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface _IHospitalRevenueHeadDataRepository extends JpaRepository< HospitalRevenueHead, Long> {

	List<HospitalRevenueHead> findAllByServiceGroup(HospitalServiceGroup serviceGroup);

	Optional<HospitalRevenueHead> findByTitleAndServiceGroup( String title, HospitalServiceGroup serviceGroup );
}
