
package com.james.medpay.features.users.domain.usecase.usersBaseUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.EmptyParam;
import com.james.medpay.features.users.domain.entity.contract.IUserEntity;
import com.james.medpay.features.users.domain.repository.contract.IUserEntityRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class UserGetAllUsecase implements UsecaseContract<List<IUserEntity>, EmptyParam> {

	private final IUserEntityRepository userEntityRepository;

	@Override
	public List<IUserEntity> execute( EmptyParam emptyParam ) {
		return userEntityRepository.getAllUsers();
	}
}
