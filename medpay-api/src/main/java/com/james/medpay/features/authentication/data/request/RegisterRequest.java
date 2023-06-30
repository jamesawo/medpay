package com.james.medpay.features.authentication.data.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RegisterRequest {
	@NotNull( message = "Username or email cannot be null" )
	@NotEmpty( message = "Username or email cannot be empty" )
	private String username;

	@NotNull( message = "Password cannot be null" )
	@NotEmpty( message = "Password cannot be empty" )
	private String password;

	@NotNull(message = "Name is required")
	String name;

	@NotNull(message = "Phone number is required")
	String phoneNumber;

	@NotNull(message = "Email address is required")
	String email;
}
