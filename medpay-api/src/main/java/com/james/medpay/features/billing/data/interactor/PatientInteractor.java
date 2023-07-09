/*
 * @Author: james.junior
 * @Date: 7/5/23 15:25
 *
 * @Project: medpay-api
 */

package com.james.medpay.features.billing.data.interactor;

import com.james.medpay.core.annotation.Interactor;
import com.james.medpay.features.billing.data.request.PatientRequest;
import com.james.medpay.features.billing.domain.entity.Patient;
import com.james.medpay.features.billing.domain.usecase.IPatientUsecase;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@Interactor
@RequiredArgsConstructor
public class PatientInteractor  {
	private final IPatientUsecase usecase;

	private Patient toModel(PatientRequest request) {
		ModelMapper mapper = new ModelMapper();
		Patient patient = mapper.map(request, Patient.class);
		LocalDate localDate = LocalDate.parse(request.getDateOfBirth(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		patient.setDateOfBirth(localDate);
		return patient;
	}

	public static PatientRequest toRequest(Patient patient) {
		ModelMapper mapper = new ModelMapper();
		PatientRequest patientRequest = mapper.map(patient, PatientRequest.class);
		patientRequest.setFullName(patient.getFullName());
		return patientRequest;
	}

	public ResponseEntity<PatientRequest> register(PatientRequest patient) {
		Patient model = this.toModel(patient);
		Patient registeredPatient = this.usecase.register(model);
		return ok().body(toRequest(registeredPatient));
	}

	public ResponseEntity<List<PatientRequest>> search(String term) {
		List<Patient> searchResult = this.usecase.search(term);
		List<PatientRequest> requestList = searchResult.stream().map(PatientInteractor::toRequest).collect(Collectors.toList());
		return ok().body(requestList);
	}
}
