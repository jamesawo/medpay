package com.james.medpay.features.hospital.domain.entity.contract;

import com.james.medpay.features.hospital.domain.entity.HospitalService;
import com.james.medpay.features.hospital.domain.entity.HospitalServiceGroup;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalServiceCategoryEnum;

import java.util.List;

public interface IHospitalRevenueHead {

	Long getId();

	void setId( Long id );

	String getTitle();

	void setTitle( String title );

	String getCode();

	void setCode( String code );

	Boolean getIsEnabled();

	void setIsEnabled( Boolean enabled );

	HospitalServiceGroup getServiceGroup();

	void setServiceGroup( HospitalServiceGroup serviceGroup );

	List<HospitalService> getHospitalServices();

	void setHospitalServices(
			List<HospitalService> hospitalServices
	);

	HospitalServiceCategoryEnum getCategory();

	void setCategory( HospitalServiceCategoryEnum category );
}
