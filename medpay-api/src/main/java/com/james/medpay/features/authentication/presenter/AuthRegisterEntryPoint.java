package com.james.medpay.features.authentication.presenter;

import com.james.medpay.features.authentication.data.interactor.contract.IAuthRegisterInteractor;
import com.james.medpay.features.authentication.data.request.RegisterRequest;
import com.james.medpay.features.users.data.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.james.medpay.core.constant.Routes.API_PREFIX;

@RestController
@RequestMapping(value = API_PREFIX + "/auth")
@RequiredArgsConstructor
public class AuthRegisterEntryPoint {

	private final IAuthRegisterInteractor interactor;

	@PostMapping(value = "/register")
	ResponseEntity<RegisterRequest> register(@Valid @RequestBody RegisterRequest request) {
		return this.interactor.register(request);
	}

}
