package com.james.medpay.features.authentication.data.interactor.implementation;

import com.james.medpay.core.annotation.Interactor;
import com.james.medpay.core.params.BiParam;
import com.james.medpay.features.authentication.data.interactor.contract.IAuthLoginInteractor;
import com.james.medpay.features.authentication.domain.entity.partial.AuthLogin;
import com.james.medpay.features.authentication.domain.usecase.authUsecase.AuthLoginUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@Interactor
@RequiredArgsConstructor
public class AuthLoginInteractorImpl implements IAuthLoginInteractor {

	private final AuthLoginUsecase loginUsecase;

	@Override
	public ResponseEntity<AuthLogin> login( String username, String password ) {
		AuthLogin login = this.loginUsecase.execute( new BiParam<>( username, password ) );
		return ResponseEntity.ok().body( login );
	}
}
