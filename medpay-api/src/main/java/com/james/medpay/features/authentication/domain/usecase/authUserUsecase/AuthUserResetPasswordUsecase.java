package com.james.medpay.features.authentication.domain.usecase.authUserUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.Param;
import com.james.medpay.core.params.BiParam;
import com.james.medpay.features.authentication.domain.repository.contract.IAuthUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class AuthUserResetPasswordUsecase implements UsecaseContract<Optional<Boolean>, Param<BiParam<String, String>>> {

	private final IAuthUserRepository repository;


	@Override
	public Optional<Boolean> execute(
			Param<BiParam<String, String>> param
	) {
		return this.repository.resetUserPassword( param.getId(), param.getModel() );
	}
}
