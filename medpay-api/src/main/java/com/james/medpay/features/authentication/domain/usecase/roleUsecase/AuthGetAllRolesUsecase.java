package com.james.medpay.features.authentication.domain.usecase.roleUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.EmptyParam;
import com.james.medpay.features.authentication.domain.entity.contract.IRole;
import com.james.medpay.features.authentication.domain.repository.contract.IAuthRoleRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class AuthGetAllRolesUsecase implements UsecaseContract<List<IRole>, EmptyParam> {
	private final IAuthRoleRepository repository;

	@Override
	public List<IRole> execute( EmptyParam param ) {
		return repository.getAll();
	}
}
