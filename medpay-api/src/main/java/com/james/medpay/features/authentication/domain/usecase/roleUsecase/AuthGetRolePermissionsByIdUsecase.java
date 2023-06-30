package com.james.medpay.features.authentication.domain.usecase.roleUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.authentication.domain.entity.contract.IPermission;
import com.james.medpay.features.authentication.domain.repository.contract.IAuthRoleRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class AuthGetRolePermissionsByIdUsecase implements UsecaseContract<List<IPermission>, Long> {

	private final IAuthRoleRepository repository;

	@Override
	public List<IPermission> execute( Long aLong ) {
		return repository.getPermissions( aLong );
	}
}
