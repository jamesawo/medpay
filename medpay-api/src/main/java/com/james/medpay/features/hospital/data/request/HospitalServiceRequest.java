package com.james.medpay.features.hospital.data.request;

import com.james.medpay.features.hospital.domain.entity.HospitalService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospitalServiceRequest {
	private Long id;
	@NotNull(message = "Service title cannot be null")
	private String title;
	private String code;
	private HospitalRevenueHeadRequest revenueHeadRequest;
	private String createdAt;
	private Boolean isEnabled;

	public HospitalServiceRequest( Long id) {
		this.id = id;
	}

	public static HospitalService toEntity(HospitalServiceRequest request){
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setSkipNullEnabled( true );
		return mapper.map(request,  HospitalService.class);
	}

	public static HospitalServiceRequest toRequest( HospitalService entity){
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setSkipNullEnabled( true );
		return mapper.map(entity,  HospitalServiceRequest.class);
	}

	public static HospitalServiceRequest trimToRequest(HospitalService entity){
		HospitalServiceRequest request = new HospitalServiceRequest();
		request.setTitle( entity.getTitle() );
		return request;
	}
}
