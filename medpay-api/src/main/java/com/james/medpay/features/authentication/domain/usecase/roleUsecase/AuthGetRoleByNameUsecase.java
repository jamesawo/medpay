
package com.james.medpay.features.authentication.domain.usecase.roleUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.authentication.domain.entity.contract.IRole;
import com.james.medpay.features.authentication.domain.repository.contract.IAuthRoleRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class AuthGetRoleByNameUsecase implements UsecaseContract<List<IRole>, String> {

	private final IAuthRoleRepository repository;

	@Override
	public List<IRole> execute( String name ) {
		return repository.getByName( name );
	}
}
