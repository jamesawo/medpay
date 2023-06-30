package com.james.medpay.features.authentication.domain.entity.partial;

import com.james.medpay.features.hospital.data.request.HospitalRequest;
import com.james.medpay.features.users.domain.entity.enums.UserTypeEnum;
import lombok.Data;

@Data
public class AuthLogin {
	private Long id;
	private String uuid;
	private String username;
	private String token;
	private UserTypeEnum type;
	private String fullName;
	private String nickName;
	private String email;
	private String menu;
	private String phone;
	private boolean enabled;
	private String profilePicUrl;
	private HospitalRequest hospital;
}
