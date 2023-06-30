package com.james.medpay.features.hospital.data.request.searchRequest;

import com.james.medpay.core.params.DateRange;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalCollectionModelEnum;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalEnvironmentEnum;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalSupportChannelEnum;
import lombok.Data;

@Data
public class HospitalSearchRequest {
	private String hospitalName;
	private String supportName;
	private HospitalSupportChannelEnum channelEnum;
	private DateRange dateRange;
	private Boolean isEnabled;
	private Boolean hasHospitalSoftware;
	private HospitalEnvironmentEnum environmentEnum;
	private HospitalCollectionModelEnum collectionModelEnum;
}
