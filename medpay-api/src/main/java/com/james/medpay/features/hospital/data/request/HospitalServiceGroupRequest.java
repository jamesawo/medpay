package com.james.medpay.features.hospital.data.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.james.medpay.features.hospital.domain.entity.HospitalServiceGroup;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@JsonInclude( JsonInclude.Include.NON_NULL )
public class HospitalServiceGroupRequest {
	private Long id;
	private String code;

	@NotNull(message = "service group title cannot be null")
	@NotEmpty(message = "service group title cannot be empty")
	private String title;

	private Boolean isEnabled;

	@NotNull(message = "hospital cannot be null")
	private HospitalRequest hospital;

	private String createdAt;

	public static HospitalServiceGroup toEntity(HospitalServiceGroupRequest request){
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setSkipNullEnabled( true );
		return mapper.map(request,  HospitalServiceGroup.class);
	}

	public static HospitalServiceGroupRequest toRequest( HospitalServiceGroup entity){
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setSkipNullEnabled( true );
		HospitalServiceGroupRequest map = mapper.map( entity, HospitalServiceGroupRequest.class );
		map.setHospital( HospitalRequest.from( entity.getHospital() ) );
		return map;
	}
}
