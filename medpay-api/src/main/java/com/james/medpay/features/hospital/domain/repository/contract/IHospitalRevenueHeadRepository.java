package com.james.medpay.features.hospital.domain.repository.contract;

import com.james.medpay.features.hospital.domain.entity.HospitalRevenueHead;
import com.james.medpay.core.params.Param;

import java.util.List;
import java.util.Optional;

public interface IHospitalRevenueHeadRepository {

	Optional<HospitalRevenueHead> createHospitalRevenueHeadInServiceGroup( Param<HospitalRevenueHead> params );

	List<HospitalRevenueHead> getAllRevenueHeadsInServiceGroup( Long serviceGroupId );

	Optional<HospitalRevenueHead> getRevenueHeadById( Long hospitalHeadId );

	Optional<HospitalRevenueHead> updateRevenueHead( HospitalRevenueHead revenueHead );

	Optional<Boolean> updateRevenueHeadStatus(Long hospitalHead, boolean status);

}
