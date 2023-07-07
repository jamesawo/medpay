package com.james.medpay.features.authentication.data.interactor.contract;

import com.james.medpay.features.authentication.data.request.RegisterRequest;
import com.james.medpay.features.authentication.domain.entity.partial.AuthLogin;
import com.james.medpay.features.users.data.request.UserRequest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IAuthRegisterInteractor {
	ResponseEntity<RegisterRequest> register(RegisterRequest request);
}
