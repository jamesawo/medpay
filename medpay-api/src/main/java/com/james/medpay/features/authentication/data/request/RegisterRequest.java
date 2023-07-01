package com.james.medpay.features.authentication.data.request;

import com.james.medpay.features.users.domain.entity.enums.UserTypeEnum;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RegisterRequest {
	@NotNull( message = "Password cannot be null" )
	@NotEmpty( message = "Password cannot be empty" )
	private String password;

	@NotNull(message = "Name is required")
	String name;

	@NotNull(message = "Phone number is required")
	String phone;

	@NotNull(message = "Email is required")
	@Email(message = "Email should be a valid email address")
	String email;

	private Long id;
	private String uuid;
	private String username;
	private Boolean isEnabled;
	private UserTypeEnum type;
	private String token;
	private String nickname;


}
