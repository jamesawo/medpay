package com.james.medpay.features.hospital.domain.repository.contract;

import com.james.medpay.features.hospital.domain.entity.HospitalService;
import java.util.List;
import java.util.Optional;

public interface IHospitalServiceRepository {

	Optional<HospitalService> createService( Long revHeadId, HospitalService service);

	Optional<Boolean> updateService( HospitalService service);

	List<HospitalService> getAllServiceInRevenueHead(Long revHeadId);

	Optional<Boolean> updateStatus(Long serId, boolean status);

	List<HospitalService> searchWithTitleAndFilterByHospital(Long hospitalId, String title);

}
