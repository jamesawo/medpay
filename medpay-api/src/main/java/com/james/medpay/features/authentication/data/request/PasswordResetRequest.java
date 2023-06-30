package com.james.medpay.features.authentication.data.request;

import com.james.medpay.features.users.data.request.UserRequest;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PasswordResetRequest {
	@NotNull(message = "user cannot be null")
	private UserRequest user;

	@NotNull( message = "password or email cannot be null" )
	@NotEmpty( message = "password or email cannot be empty" )
	private String password;

	@NotNull( message = "confirm password cannot be null" )
	@NotEmpty( message = "confirm password cannot be empty" )
	private String confirmPassword;
}
