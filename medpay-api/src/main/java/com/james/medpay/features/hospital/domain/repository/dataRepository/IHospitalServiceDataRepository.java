package com.james.medpay.features.hospital.domain.repository.dataRepository;

import com.james.medpay.features.hospital.domain.entity.HospitalRevenueHead;
import com.james.medpay.features.hospital.domain.entity.HospitalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IHospitalServiceDataRepository extends JpaRepository< HospitalService, Long > {

	@Query(value = "update HospitalService h set h.isEnabled = :status where h.id = :id")
	int updateStatusById(@Param(value = "status") boolean status, @Param(value = "id") Long id);

	List< HospitalService > findAllByRevenueHead(HospitalRevenueHead revenueHead);

	List< HospitalService > findAllByTitleIsContainingIgnoreCase(String title);

	Optional<HospitalService> findByRevenueHeadAndTitleIgnoreCase( HospitalRevenueHead revenueHead, String title );

}
