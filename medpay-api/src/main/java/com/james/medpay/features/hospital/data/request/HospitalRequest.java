package com.james.medpay.features.hospital.data.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.james.medpay.features.hospital.domain.entity.HospitalDetail;
import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalCollectionModelEnum;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotNull;

@Data
@JsonInclude( JsonInclude.Include.NON_NULL )
public class HospitalRequest {
	private Long id;
	private String uuid;
	private Boolean isEnabled = true;
	@NotNull( message = "ExpiryDate is required" )
	private String expiryDate;

	@NotNull( message = "UseSoftware is required" )
	private Boolean hasHospitalSoftware;

	@NotNull( message = "CollectionModel is required" )
	private HospitalCollectionModelEnum collectionModelEnum;

	private String startDate;

	private HospitalBasicDetailRequest detail;
	@JsonIgnore
	private HospitalServiceGroupRequest serviceGroup;
	private HospitalApiConfigurationRequest apiConfiguration;
	private HospitalSettlementConfigurationRequest settlementConfiguration;

	public HospitalRequest() {
	}

	public HospitalRequest( Long id ) {
		this.id = id;
	}


	public static HospitalRequest from( HospitalEntity entity ) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setSkipNullEnabled( true );
		HospitalRequest request = modelMapper.map( entity, HospitalRequest.class );
		if ( ObjectUtils.isNotEmpty( entity.getDetail() ) ) {
			request.setDetail( HospitalBasicDetailRequest.toRequest( entity.getDetail() ) );
		}
		return request;
	}

	public static HospitalEntity to( HospitalRequest request ) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
		           .setSkipNullEnabled( true );
		return modelMapper.map( request, HospitalEntity.class );
	}

	public static HospitalRequest trimRequest(HospitalEntity entity){
		HospitalRequest request = new HospitalRequest();
		request.setId( entity.getId() );
		HospitalDetail detail = entity.getDetail();
		request.setDetail( new HospitalBasicDetailRequest( detail.getId(), detail.getName()) );
		return request;
	}
}
