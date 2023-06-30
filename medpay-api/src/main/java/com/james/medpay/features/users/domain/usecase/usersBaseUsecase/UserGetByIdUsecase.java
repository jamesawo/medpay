
package com.james.medpay.features.users.domain.usecase.usersBaseUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.users.domain.entity.contract.IUserEntity;
import com.james.medpay.features.users.domain.repository.contract.IUserEntityRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class UserGetByIdUsecase implements UsecaseContract<Optional<IUserEntity>, Long> {

	private final IUserEntityRepository userEntityRepository;


	@Override
	public Optional<IUserEntity> execute( Long userId ) {
		return userEntityRepository.getUserById( userId );
	}
}
