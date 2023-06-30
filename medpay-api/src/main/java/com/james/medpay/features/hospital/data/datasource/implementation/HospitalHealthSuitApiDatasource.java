
package com.james.medpay.features.hospital.data.datasource.implementation;

import com.james.medpay.features.hospital.data.datasource.contract.IHospitalApiDataSourceIntegrationContract;
import com.james.medpay.features.hospital.data.datasource.model.RequestPayload;
import com.james.medpay.features.hospital.domain.entity.HospitalApiConfiguration;
import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static java.time.Duration.ofSeconds;
import static org.springframework.http.HttpMethod.resolve;

@Service("hsApiDataSourceImplementation")
public class HospitalHealthSuitApiDatasource implements IHospitalApiDataSourceIntegrationContract {

	public static final String CLIENT_ERROR = "Please try again later, Third-party connection failed.";
	public static final long CLIENT_CONN_TIMEOUT = 350;

	private final RestTemplate restTemplate;

	public HospitalHealthSuitApiDatasource( RestTemplateBuilder restTemplateBuilder ) {
		this.restTemplate = restTemplateBuilder
				.setConnectTimeout( ofSeconds( CLIENT_CONN_TIMEOUT ) )
				.setReadTimeout( ofSeconds( CLIENT_CONN_TIMEOUT ) )
				.build();
	}

	@Override
	public ResponseEntity<?> getBillDetailsFromApi( String billNumber, HospitalEntity hospital ) {
		HospitalApiConfiguration apiConfiguration = hospital.getApiConfiguration();
		String searchBillEndpoint = apiConfiguration.getSearchBillEndpoint();
		HttpMethod method = resolve( apiConfiguration.getSearchBillMethod().name() );
		RequestPayload payload = this._createRequestPayload( apiConfiguration, searchBillEndpoint+billNumber, method );
		return this._sendHttpRequest( payload );
	}

	@Override
	public ResponseEntity<?> sendBillIsPaidNotificationToApi( String billNumber, HospitalEntity hospital ) {
		HospitalApiConfiguration apiConfiguration = hospital.getApiConfiguration();
		String payBillEndpoint = apiConfiguration.getPayBillEndpoint();
		HttpMethod method = resolve( apiConfiguration.getPayBillMethod().name() );
		RequestPayload payload = this._createRequestPayload( apiConfiguration, payBillEndpoint+billNumber, method );
		return this._sendHttpRequest( payload );
	}

	@Override
	public ResponseEntity<?> getBillReceiptFromApi( String billNumber, HospitalEntity hospital ) {
		return null;
	}

	@Override
	public ResponseEntity<?> getBillPaymentStatusFromApi(String billNumber, HospitalEntity hospital ) {
		return null;
	}

	@Override
	public ResponseEntity<?> sendPingRequestToApiConnection( HospitalEntity hospital ) {
		return null;
	}

	private RequestPayload _createRequestPayload( HospitalApiConfiguration configuration, String url, HttpMethod method ) {
		return RequestPayload.build( configuration, url, method );
	}

	private ResponseEntity<Object> _sendHttpRequest( RequestPayload req ) {
		try {
			return restTemplate.exchange( req.getUrl(), req.getMethod(), req.getHttpEntity(), Object.class, req.getUrlParam() );
		} catch ( Exception e ) {
			throw new RestClientException( e.getMessage() != null ? e.getMessage() : CLIENT_ERROR, e );
		}
	}

}
