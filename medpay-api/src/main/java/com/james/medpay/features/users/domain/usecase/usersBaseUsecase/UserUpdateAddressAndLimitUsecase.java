
package com.james.medpay.features.users.domain.usecase.usersBaseUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.users.domain.entity.UserEntity;
import com.james.medpay.features.users.domain.entity.contract.IUserEntity;
import com.james.medpay.features.users.domain.repository.contract.IUserEntityRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class UserUpdateAddressAndLimitUsecase implements UsecaseContract<Optional<IUserEntity>, UserEntity> {

	private final IUserEntityRepository repository;


	@Override
	public Optional<IUserEntity> execute( UserEntity user ) {
		return repository.updateAddressAndLimit( user );
	}
}
