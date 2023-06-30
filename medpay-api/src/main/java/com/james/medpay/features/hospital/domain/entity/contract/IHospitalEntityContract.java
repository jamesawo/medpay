package com.james.medpay.features.hospital.domain.entity.contract;

import com.james.medpay.features.hospital.domain.entity.HospitalApiConfiguration;
import com.james.medpay.features.hospital.domain.entity.HospitalDetail;
import com.james.medpay.features.hospital.domain.entity.HospitalServiceGroup;
import com.james.medpay.features.hospital.domain.entity.HospitalSettlementConfiguration;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalCollectionModelEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface IHospitalEntityContract {

	Long getId();

	void setId( Long id );

	String getUuid();

	void setUuid( String uuid );

	Boolean getIsEnabled();

	void setIsEnabled( Boolean enabled );

	Boolean getHasHospitalSoftware();

	void setHasHospitalSoftware( Boolean hasHospitalSoftware );

	HospitalApiConfiguration getApiConfiguration();

	void setApiConfiguration( HospitalApiConfiguration apiConfiguration );

	HospitalCollectionModelEnum getCollectionModelEnum();

	void setCollectionModelEnum(
			HospitalCollectionModelEnum collectionModelEnum
	);

	HospitalServiceGroup getServiceGroup();

	void setServiceGroup( HospitalServiceGroup serviceGroup );

	HospitalSettlementConfiguration getSettlementConfiguration();

	void setSettlementConfiguration(
			HospitalSettlementConfiguration settlementConfiguration
	);

	HospitalDetail getDetail();

	void setDetail( HospitalDetail detail );

	String getOther();

	void setOther( String other );

	LocalDate getExpiryDate();

	void setExpiryDate( LocalDate expiryDate );

	LocalDateTime getCreatedAt();

	void setCreatedAt( LocalDateTime createdAt );

	String getCreatedBy();

	void setCreatedBy( String createdBy );

	LocalDateTime getUpdatedAt();

	void setUpdatedAt( LocalDateTime updatedAt );

	String getLastUpdatedBy();

	void setLastUpdatedBy( String lastUpdatedBy );

	String getDeletedBy();

	void setDeletedBy( String deletedBy );

	LocalDate getDeletedAt();

	void setDeletedAt( LocalDate deletedAt );
}
