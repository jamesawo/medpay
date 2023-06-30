package com.james.medpay.features.hospital.domain.entity.contract;

import com.james.medpay.features.hospital.domain.entity.enums.HospitalSettlementChargeEnum;

public interface IHospitalSettlementConfiguration {

	Long getId();

	void setId( Long id );

	HospitalSettlementChargeEnum getSettlementChargeEnum();

	void setSettlementChargeEnum(
			HospitalSettlementChargeEnum settlementChargeEnum
	);
}
