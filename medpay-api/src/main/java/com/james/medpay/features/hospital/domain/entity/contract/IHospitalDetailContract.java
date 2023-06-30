package com.james.medpay.features.hospital.domain.entity.contract;


public interface IHospitalDetailContract {

	Long getId();

	void setId( Long id );

	String getName();

	void setName( String name );

	String getCode();

	void setCode( String code );

	String getHospitalLogoUrl();

	void setHospitalLogoUrl( String hospitalLogoUrl );

	String getSupportName();

	void setSupportName( String supportName );

	String getSupportPhone();

	void setSupportPhone( String supportPhone );

	String getSupportEmail();

	void setSupportEmail( String supportEmail );

	String getHospitalAddress();

	void setHospitalAddress( String hospitalAddress );
}
