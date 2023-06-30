package com.james.medpay.features.hospital.data.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalCollectionModelEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@JsonInclude( JsonInclude.Include.NON_NULL )
public class HospitalPartialDetailRequest {
	private Long id;

	@NotNull(message = "isEnabled cannot be null")
	private Boolean isEnabled;

	@NotNull(message = "hasHospitalSoftware cannot be null")
	private Boolean hospitalHasSoftware;

	@NotNull(message = "expiryDate cannot be null")
	private LocalDate expiryDate;

	@NotNull(message = "collectionModelEnum cannot be null")
	private HospitalCollectionModelEnum collectionMode;


	public static HospitalEntity toEntity( HospitalPartialDetailRequest request ) {
		HospitalEntity hospital = new HospitalEntity();
		hospital.setIsEnabled( request.getIsEnabled() );
		hospital.setHasHospitalSoftware( request.getHospitalHasSoftware() );
		hospital.setExpiryDate( request.getExpiryDate() );
		hospital.setCollectionModelEnum( request.getCollectionMode() );
		return hospital;
	}

	public static HospitalPartialDetailRequest toRequest( HospitalEntity entity ) {
		HospitalPartialDetailRequest request = new HospitalPartialDetailRequest();
		request.setIsEnabled( entity.getIsEnabled() );
		request.setHospitalHasSoftware( entity.getHasHospitalSoftware() );
		request.setExpiryDate( entity.getExpiryDate() );
		request.setCollectionMode( entity.getCollectionModelEnum() );
		return request;
	}

}
