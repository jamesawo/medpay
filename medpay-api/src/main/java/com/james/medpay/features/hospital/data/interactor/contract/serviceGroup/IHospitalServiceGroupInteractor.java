package com.james.medpay.features.hospital.data.interactor.contract.serviceGroup;

import com.james.medpay.features.hospital.data.request.HospitalServiceGroupRequest;

import java.util.List;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IHospitalServiceGroupInteractor {

	ResponseEntity<Optional<HospitalServiceGroupRequest>> updateHospitalServiceGroup( HospitalServiceGroupRequest request);

	ResponseEntity< List<HospitalServiceGroupRequest>> getServiceGroupByHospitalId(Long hospitalId);
}