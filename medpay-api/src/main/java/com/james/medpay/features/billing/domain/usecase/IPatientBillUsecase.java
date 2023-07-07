package com.james.medpay.features.billing.domain.usecase;

import com.james.medpay.features.billing.data.request.PatientRequest;
import com.james.medpay.features.billing.domain.entity.PatientBill;
import com.james.medpay.features.billing.domain.enums.BillStatus;

import java.util.List;

public interface IPatientBillUsecase {

	PatientBill register(PatientBill request);

	List<PatientBill> search(String term);

	void updateBillStatus(String billNumber, BillStatus status);
}
