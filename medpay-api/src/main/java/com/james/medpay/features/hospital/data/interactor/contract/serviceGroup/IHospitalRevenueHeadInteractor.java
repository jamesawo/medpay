package com.james.medpay.features.hospital.data.interactor.contract.serviceGroup;

import com.james.medpay.features.hospital.data.request.HospitalRevenueHeadRequest;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

public interface IHospitalRevenueHeadInteractor {
	ResponseEntity< Optional<HospitalRevenueHeadRequest> > createRevenueHead(Long serviceGroupId, HospitalRevenueHeadRequest request);

	ResponseEntity< Optional<Boolean> > updateStatus(Long revId, boolean status);

	ResponseEntity<Optional<HospitalRevenueHeadRequest>> updateRevenueHead( HospitalRevenueHeadRequest request );
}
