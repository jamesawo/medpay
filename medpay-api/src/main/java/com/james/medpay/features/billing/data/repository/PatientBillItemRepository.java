package com.james.medpay.features.billing.data.repository;

import com.james.medpay.features.billing.domain.entity.PatientBillItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientBillItemRepository extends JpaRepository<PatientBillItem, Long> {
}
