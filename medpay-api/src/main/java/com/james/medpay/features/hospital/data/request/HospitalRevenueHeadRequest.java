package com.james.medpay.features.hospital.data.request;

import com.james.medpay.features.hospital.domain.entity.HospitalRevenueHead;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalServiceCategoryEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;

@Data
public class HospitalRevenueHeadRequest {
	private Long id;

	@NotNull(message = "Title cannot be null")
	private String title;

	@NotNull(message = "isEnabled cannot be null")
	private Boolean isEnabled;

	private HospitalServiceGroupRequest serviceGroup;

	private HospitalServiceCategoryEnum category;

	private String createdAt;


	public static HospitalRevenueHead toEntity(HospitalRevenueHeadRequest request){
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setSkipNullEnabled( true );
		return mapper.map(request,  HospitalRevenueHead.class);
	}

	public static HospitalRevenueHeadRequest toRequest( HospitalRevenueHead entity){
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setSkipNullEnabled( true );
		HospitalRevenueHeadRequest map = mapper.map( entity, HospitalRevenueHeadRequest.class );
		map.setCreatedAt( entity.getCreatedAt().toString() );
		return map;
	}

}
