
package com.james.medpay.features.users.domain.usecase.usersBaseUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.users.domain.entity.UserEntity;
import com.james.medpay.features.users.domain.entity.contract.IUserEntity;
import com.james.medpay.features.users.domain.repository.contract.IUserEntityRepository;

@UseCase
public class UserCreateUsecase implements UsecaseContract<IUserEntity, UserEntity> {

	private final IUserEntityRepository userEntityRepository;

	public UserCreateUsecase(IUserEntityRepository userEntityRepository) {
		this.userEntityRepository = userEntityRepository;
	}


	@Override
	public IUserEntity execute( UserEntity userEntity ) {
		return userEntityRepository.createUser( userEntity );
	}
}
