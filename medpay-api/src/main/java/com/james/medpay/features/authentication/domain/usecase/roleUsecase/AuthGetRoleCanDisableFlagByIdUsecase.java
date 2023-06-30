package com.james.medpay.features.authentication.domain.usecase.roleUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.authentication.domain.repository.contract.IAuthRoleRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class AuthGetRoleCanDisableFlagByIdUsecase implements UsecaseContract<Optional<Boolean>, Long> {

	private final IAuthRoleRepository repository;

	@Override
	public Optional<Boolean> execute( Long id ) {
		return repository.getCanDisableFlag( id );
	}
}
