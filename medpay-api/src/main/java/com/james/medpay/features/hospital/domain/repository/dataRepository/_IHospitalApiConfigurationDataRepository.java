package com.james.medpay.features.hospital.domain.repository.dataRepository;

import com.james.medpay.features.hospital.domain.entity.HospitalApiConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface _IHospitalApiConfigurationDataRepository extends JpaRepository<HospitalApiConfiguration, Long> {
}
