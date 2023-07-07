/*
 * @Author: james.junior
 * @Date: 7/5/23 12:52
 *
 * @Project: medpay-api
 */

package com.james.medpay.features.billing.data.usecase_impl;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.constant.AppDefaultConstant;
import com.james.medpay.features.billing.data.repository.PatientRepository;
import com.james.medpay.features.billing.data.specification.PatientSearchSpecification;
import com.james.medpay.features.billing.domain.entity.Patient;
import com.james.medpay.features.billing.domain.usecase.IPatientUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static com.james.medpay.core.constant.AppDefaultConstant.DEFAULT_CODE_START;

@UseCase
@RequiredArgsConstructor
public class PatientUsecaseImpl implements IPatientUsecase {

	private final PatientRepository repository;


	@Override
	public Patient register(Patient patient) {
		Integer number = this.generatePatientNumber();
		String prefix = "PN";

		patient.setPatientNumber(number);
		patient.setPatientNumberPrefix(prefix);
		patient.setUniqueCode(String.format("%s%s", prefix, number));
		return this.repository.save(patient);
	}

	private Integer generatePatientNumber() {
		Optional<Patient> optionalPatient = this.repository.findTopByOrderByIdDesc();
		return optionalPatient.map(patient -> new AtomicInteger(patient.getPatientNumber()).incrementAndGet()).orElse(DEFAULT_CODE_START);
	}


	@Override
	public List<Patient> search(String term) {
		Specification<Patient> specification = PatientSearchSpecification.search(term);
		return this.repository.findAll(specification);
	}

	public Optional<Patient> findOne(Long id){
		return this.repository.findById(id);
	}


}
