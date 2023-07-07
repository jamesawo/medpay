package com.james.medpay.features.billing.data.repository;

import com.james.medpay.features.billing.domain.entity.Patient;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> , JpaSpecificationExecutor<Patient> {
	Optional<Patient> findTopByOrderByIdDesc();

	List<Patient> findAll(Specification<Patient> specification);
}
