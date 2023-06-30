
package com.james.medpay.features.authentication.domain.usecase.roleUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.authentication.domain.entity.contract.IRole;
import com.james.medpay.features.authentication.domain.repository.contract.IAuthRoleRepository;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class AuthCreateRoleUsecase implements UsecaseContract<IRole, IRole> {
	private final IAuthRoleRepository repository;

	@Override
	public IRole execute( IRole role ) {
		return repository.create( role );
	}
}
