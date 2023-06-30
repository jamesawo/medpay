package com.james.medpay.features.authentication.domain.usecase.roleUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.authentication.domain.entity.contract.IPermission;
import com.james.medpay.features.authentication.domain.repository.contract.IAuthRoleRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class AuthUpdateRolePermissionsByIdUsecase implements UsecaseContract<Optional<Boolean>, Param<List<IPermission>>> {
	private final IAuthRoleRepository repository;

	@Override
	public Optional<Boolean> execute( Param<List<IPermission>> param ) {
		return repository.updatePermissions( param.getId(), param.getModel() );
	}
}
