package com.james.medpay.features.authentication.data.interactor.contract;

import com.james.medpay.features.authentication.domain.entity.partial.AuthLogin;
import org.springframework.http.ResponseEntity;

public interface IAuthLoginInteractor {
	ResponseEntity<AuthLogin> login( String username, String password );
}
