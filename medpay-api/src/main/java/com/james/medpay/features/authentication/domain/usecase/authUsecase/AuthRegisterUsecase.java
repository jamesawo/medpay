package com.james.medpay.features.authentication.domain.usecase.authUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.BiParam;
import com.james.medpay.features.authentication.domain.entity.partial.AuthLogin;
import com.james.medpay.features.authentication.domain.repository.contract.IAuthLoginRepository;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class AuthRegisterUsecase implements UsecaseContract<AuthLogin, BiParam<String, String>> {
	private final IAuthLoginRepository loginRepository;

	@Override
	public AuthLogin execute( BiParam<String, String> param ) {
		return loginRepository.login( param.getLeft(), param.getRight() );
	}
}
