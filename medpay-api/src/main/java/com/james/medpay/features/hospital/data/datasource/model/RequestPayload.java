
package com.james.medpay.features.hospital.data.datasource.model;

import com.james.medpay.features.hospital.domain.entity.HospitalApiConfiguration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestPayload {
	HttpMethod method;
	String url;
	HttpEntity<String> httpEntity;
	String urlParam;


	public static RequestPayload build( HospitalApiConfiguration cof, String url, HttpMethod method ) {
		switch ( cof.getEnvironmentEnum() ) {
			case LIVE:
				return RequestPayload
						.builder().method( method ).url( cof.getApiBaseUrl() + url )
						.httpEntity( createBasicHttpAuthHeader( cof.getPublicToken(), cof.getPrivateToken() ) ).build();
			case TEST:
				return RequestPayload
						.builder().method( method ).url( cof.getApiTestBaseUrl() + url )
						.httpEntity( createBasicHttpAuthHeader( cof.getPublicToken(), cof.getPrivateToken() ) ).build();
			default:
				return new RequestPayload();
		}
	}

	// todo:: implement other auth types in [HospitalAuthTypeEnum]
	public static HttpEntity<String> createBasicHttpAuthHeader( String username, String password ) {
		HttpHeaders authorization = new HttpHeaders() {{
			String auth = username + ":" + password;
			String encodedString = new String( Base64.encodeBase64( auth.getBytes() ) );
			String authHeader = "Basic " + encodedString;
			set( "Authorization", authHeader );
		}};
		return new HttpEntity<>( authorization );
	}
}
