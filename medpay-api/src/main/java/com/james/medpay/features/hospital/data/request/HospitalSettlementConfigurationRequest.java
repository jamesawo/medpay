package com.james.medpay.features.hospital.data.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalSettlementChargeEnum;
import lombok.Data;

@Data
@JsonInclude( JsonInclude.Include.NON_NULL )
public class HospitalSettlementConfigurationRequest {
	// settlement configuration
	private Long settlementId;
	private HospitalSettlementChargeEnum settlementChargeEnum;
}
