package com.james.medpay.features.hospital.data.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.james.medpay.features.hospital.domain.entity.HospitalApiConfiguration;
import com.james.medpay.features.hospital.domain.entity.contract.IHospitalApiConfiguration;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalAuthTypeEnum;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalEnvironmentEnum;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalMethodEnum;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotNull;

@Data
@JsonInclude( JsonInclude.Include.NON_NULL )
public class HospitalApiConfigurationRequest {

	private Long id;

	@NotNull(message = "Hospital api live url cannot be null")
	private String apiBaseUrl;

	@NotNull(message = "Hospital api test url cannot be null")
	private String apiTestBaseUrl;

	@NotNull(message = "Hospital api auth type cannot be null")
	private HospitalAuthTypeEnum authenticationType;

	private String publicToken;

	private String privateToken;

	private String apiKey;

	private String oauthToken;

	@NotNull(message = "Hospital active environment cannot be null")
	private HospitalEnvironmentEnum environmentEnum;

	@NotNull(message = "Hospital search bill method cannot be null")
	private HospitalMethodEnum searchBillMethod;

	@NotNull(message = "Hospital pay bill method cannot be null")
	private HospitalMethodEnum payBillMethod;

	@NotNull(message = "Hospital search bill endpoint cannot be null")
	private String searchBillEndpoint;

	@NotNull(message = "Hospital pay bill endpoint cannot be null")
	private String payBillEndpoint;


	public static HospitalApiConfigurationRequest toRequest( IHospitalApiConfiguration configuration ) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setSkipNullEnabled( true );
		return modelMapper.map( configuration, HospitalApiConfigurationRequest.class );
	}

	public static HospitalApiConfiguration toEntity( HospitalApiConfigurationRequest request ) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setSkipNullEnabled( true );
		return modelMapper.map( request, HospitalApiConfiguration.class );
	}

}
