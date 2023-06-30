
package com.james.medpay.features.users.domain.usecase.usersBaseUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.users.domain.entity.enums.UserTypeEnum;
import com.james.medpay.features.users.domain.repository.contract.IUserEntityRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class UserUpdateTypeDateUsecase implements UsecaseContract<Optional<Boolean>, Param<UserTypeEnum>> {

	private final IUserEntityRepository repository;

	@Override
	public Optional<Boolean> execute( Param<UserTypeEnum> param ) {
		return repository.updateUserTypeById( param.getId(), param.getModel() );
	}
}
