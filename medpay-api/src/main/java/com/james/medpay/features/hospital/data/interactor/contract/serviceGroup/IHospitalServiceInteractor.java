package com.james.medpay.features.hospital.data.interactor.contract.serviceGroup;

import com.james.medpay.features.hospital.data.request.HospitalServiceRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IHospitalServiceInteractor {

	ResponseEntity<Optional<HospitalServiceRequest>> create( Long revHeadId, HospitalServiceRequest request );

	ResponseEntity<Optional<Boolean>> update( HospitalServiceRequest request );

	ResponseEntity<List<HospitalServiceRequest>> getAllByRevenueHead( Long revHeadId );

	ResponseEntity<Optional<Boolean>> updateStatus( Long serId, boolean status );

	ResponseEntity<List<HospitalServiceRequest>> searchByTitleAndHospital( Long hospitalId, String title );

}
