package com.james.medpay.features.billing.data.repository;

import com.james.medpay.features.billing.domain.entity.PatientBill;
import com.james.medpay.features.billing.domain.enums.BillStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PatientBillRepository extends JpaRepository<PatientBill, Long>, JpaSpecificationExecutor<PatientBill> {

	@Query(value = "update PatientBill b set b.status = :status where b.billNumber = :billNumber")
	@Modifying
	int updatePatientBillStatusByBillNumber(@Param(value = "status") BillStatus status, @Param(value = "billNumber")String billNumber);

	Optional<PatientBill> findTopByOrderByIdDesc();

	@Override
	List<PatientBill> findAll(Specification<PatientBill> specification);
}
