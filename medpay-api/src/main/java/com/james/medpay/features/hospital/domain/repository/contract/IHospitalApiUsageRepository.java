package com.james.medpay.features.hospital.domain.repository.contract;

import com.james.medpay.features.hospital.domain.entity.partial.HospitalBill;

import java.util.Optional;

public interface IHospitalApiUsageRepository {

	Optional<HospitalBill> apiSearchForBillDetail( Long hospitalId, String billNumber );

	Optional<Boolean> apiSendBillPayment( Long hospitalId, String billNumber );

	Optional<Boolean> apiCheckPaymentStatus( Long hospitalId, String billNumber );

	Optional<Boolean> apiCheckIsConnectionAlive( Long hospitalId );
}
