
package com.james.medpay.features.authentication.domain.usecase.roleUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.authentication.domain.entity.contract.IRole;
import com.james.medpay.features.authentication.domain.repository.contract.IAuthRoleRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class AuthUpdateRoleByIdUsecase implements UsecaseContract<Optional<IRole>, Param<IRole>> {
	private final IAuthRoleRepository repository;

	@Override
	public Optional<IRole> execute( Param<IRole> param ) {
		return repository.updateNameAndStatus( param.getId(), param.getModel() );
	}
}
