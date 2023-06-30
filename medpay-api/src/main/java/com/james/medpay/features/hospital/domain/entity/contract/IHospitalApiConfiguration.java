package com.james.medpay.features.hospital.domain.entity.contract;

import com.james.medpay.features.hospital.domain.entity.enums.HospitalAuthTypeEnum;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalEnvironmentEnum;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalMethodEnum;

public interface IHospitalApiConfiguration {

	Long getId();

	void setId( Long id );

	String getApiBaseUrl();

	void setApiBaseUrl( String apiBaseUrl );

	HospitalAuthTypeEnum getAuthenticationType();

	void setAuthenticationType(
			HospitalAuthTypeEnum authenticationType
	);

	String getPublicToken();

	void setPublicToken( String token );

	String getPrivateToken();

	void setPrivateToken( String privateToken );

	String getApiKey();

	void setApiKey( String apiKey );

	String getOauthToken();

	void setOauthToken( String oauthToken );

	HospitalEnvironmentEnum getEnvironmentEnum();

	void setEnvironmentEnum( HospitalEnvironmentEnum environmentEnum );

	void setSearchBillMethod( HospitalMethodEnum httpMethod );

	HospitalMethodEnum getSearchBillMethod();

	void setPayBillMethod( HospitalMethodEnum httpMethod );

	HospitalMethodEnum getPayBillMethod();

	void setSearchBillEndpoint( String endpoint );

	String getSearchBillEndpoint();

	void setPayBillEndpoint( String endpoint );

	String getPayBillEndpoint();

}
