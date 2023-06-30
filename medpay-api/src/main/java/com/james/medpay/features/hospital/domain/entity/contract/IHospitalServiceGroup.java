package com.james.medpay.features.hospital.domain.entity.contract;

import com.james.medpay.features.hospital.domain.entity.HospitalRevenueHead;

import java.util.List;

public interface IHospitalServiceGroup {

	Long getId();

	void setId( Long id );

	String getCode();

	void setCode( String code );

	String getTitle();

	void setTitle( String title );

	Boolean getIsEnabled();

	void setIsEnabled( Boolean enabled );

	List<HospitalRevenueHead> getRevenueHeads();

	void setRevenueHeads(
			List<HospitalRevenueHead> revenueHeads
	);

}
