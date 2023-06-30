package com.james.medpay.features.hospital.domain.entity.contract;

import com.james.medpay.features.hospital.domain.entity.HospitalRevenueHead;

public interface IHospitalService {

	Long getId();

	void setId( Long id );

	String getTitle();

	void setTitle( String title );

	String getCode();

	void setCode( String code );

	HospitalRevenueHead getRevenueHead();

	void setRevenueHead( HospitalRevenueHead revenueHead );

	Boolean getIsEnabled();

	void setIsEnabled(Boolean isEnabled);
}
