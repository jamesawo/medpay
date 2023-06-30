package com.james.medpay.features.authentication.domain.usecase.permissionUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.EmptyParam;
import com.james.medpay.features.authentication.domain.entity.contract.IPermission;
import com.james.medpay.features.authentication.domain.repository.contract.IAuthPermissionRepository;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@UseCase
@RequiredArgsConstructor
public class AuthPermissionGetAllUsecase implements UsecaseContract<Set<IPermission>, EmptyParam> {
	private final IAuthPermissionRepository repository;

	@Override
	public Set<IPermission> execute( EmptyParam param ) {
		return repository.findAll();
	}
}
