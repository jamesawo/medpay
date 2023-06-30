package com.james.medpay.features.authentication.domain.usecase.roleUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.authentication.domain.repository.contract.IAuthRoleRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class AuthUpdateRoleStatusByIdUsecase implements UsecaseContract<Optional<Boolean>, Param<Boolean>> {
	private final IAuthRoleRepository repository;

	@Override
	public Optional<Boolean> execute( Param<Boolean> param ) {
		return repository.updateStatusById( param.getId(), param.getModel() );
	}
}
