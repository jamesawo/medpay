package com.james.medpay.features.authentication.presenter;

import com.james.medpay.features.authentication.data.interactor.contract.IAuthLoginInteractor;
import com.james.medpay.features.authentication.data.request.LoginRequest;
import com.james.medpay.features.authentication.domain.entity.partial.AuthLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.james.medpay.core.constant.Routes.API_PREFIX;

@RestController
@RequestMapping( value = API_PREFIX + "/auth" )
@RequiredArgsConstructor
public class AuthRegisterEntryPoint {

	private final IAuthLoginInteractor interactor;

	@PostMapping( value = "/register" )
	ResponseEntity<AuthLogin> login(
			@Valid @RequestBody LoginRequest request
	) {
		return this.interactor.login( request.getUsername(), request.getPassword() );
	}

}
