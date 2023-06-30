
package com.james.medpay.features.users.data.request;

import com.james.medpay.core.params.DateRange;
import com.james.medpay.features.hospital.data.request.HospitalRequest;
import com.james.medpay.features.users.domain.entity.enums.GenderEnum;
import com.james.medpay.features.users.domain.entity.enums.UserTypeEnum;
import lombok.Data;

@Data
public class UserSearchRequest {
	private String name;
	private String phoneNumber;
	private String email;
	private Boolean isEnabled;
	private DateRange dateRange;
	private UserTypeEnum userTypeEnum;
	private GenderEnum genderEnum;
	private HospitalRequest hospital;
}
