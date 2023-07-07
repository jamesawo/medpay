package com.james.medpay.features.billing.domain.usecase;

import com.james.medpay.features.billing.data.request.PatientRequest;
import com.james.medpay.features.billing.domain.entity.Patient;

import java.util.List;
import java.util.Optional;

public interface IPatientUsecase {
	Patient register(Patient patient);

	List<Patient> search(String term);

	Optional<Patient> findOne(Long id);
}
