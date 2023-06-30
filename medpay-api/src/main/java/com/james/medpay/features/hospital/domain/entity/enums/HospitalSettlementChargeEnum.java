package com.james.medpay.features.hospital.domain.entity.enums;

public enum HospitalSettlementChargeEnum {
	/**
	 * for EXTRA_CHARGE,
	 * <p>
	 * app will add extra service charge to payment bill amount
	 */
	EXTRA_CHARGE,

	/**
	 * For EMBEDDED_CHARGE,
	 * <p>
	 * application service charge is paid by the hospital,
	 */
	EMBEDDED_CHARGE
}
