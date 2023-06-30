package com.james.medpay.features.authentication.domain.usecase.authUserUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.authentication.domain.entity.Role;
import com.james.medpay.features.authentication.domain.repository.contract.IAuthUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@UseCase
@RequiredArgsConstructor
public class AuthUserGetRolesUsecase implements UsecaseContract<Collection<Role>, Long> {

	private final IAuthUserRepository repository;

	@Override
	public Collection<Role> execute( Long userId ) {
		return repository.getUserRoles( userId );
	}
}
