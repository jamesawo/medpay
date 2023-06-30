package com.james.medpay.features.authentication.domain.usecase.authUserUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.authentication.domain.entity.Role;
import com.james.medpay.features.authentication.domain.repository.contract.IAuthUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class AuthUserAssignRolesUsecase implements UsecaseContract<Optional<Boolean>, Param<List<Role>>> {

	private final IAuthUserRepository repository;

	@Override
	public Optional<Boolean> execute(
			Param<List<Role>> param
	) {
		return repository.assignRolesToUser(param.getId(), param.getModel());
	}
}
