package com.james.medpay.features.hospital.data.request.searchRequest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.james.medpay.core.params.DateRange;
import com.james.medpay.features.hospital.data.request.HospitalRequest;
import lombok.Data;

@Data
@JsonInclude( JsonInclude.Include.NON_NULL )
public class HospitalServiceGroupSearchRequest {
	private String title;
	private Boolean isEnabled;
	private HospitalRequest hospital;
	private DateRange dateRange;
}
