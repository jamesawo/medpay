package com.james.medpay.features.hospital.data.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.james.medpay.features.hospital.domain.entity.HospitalDetail;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalSupportChannelEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotNull;

@Data
@JsonInclude( JsonInclude.Include.NON_NULL )
@NoArgsConstructor
public class HospitalBasicDetailRequest {
	private Long id;
	@NotNull( message = "Hospital name cannot be null" )
	private String name;
	private String hospitalLogoUrl;
	private String supportName;
	private String supportPhone;
	private String supportEmail;
	private String hospitalAddress;
	private HospitalSupportChannelEnum supportChannel;

	public HospitalBasicDetailRequest( Long id, String name ) {
		this.id = id;
		this.name = name;
	}

	public static HospitalDetail toEntity( HospitalBasicDetailRequest request ) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map( request, HospitalDetail.class );
	}

	public static HospitalBasicDetailRequest toRequest( HospitalDetail entity ) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map( entity, HospitalBasicDetailRequest.class );
	}

}
