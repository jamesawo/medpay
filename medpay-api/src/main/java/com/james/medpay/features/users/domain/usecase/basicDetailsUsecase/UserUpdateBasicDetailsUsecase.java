
package com.james.medpay.features.users.domain.usecase.basicDetailsUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.users.domain.entity.UserBasicDetailsEntity;
import com.james.medpay.features.users.domain.entity.contract.IUserBasicDetailsEntity;
import com.james.medpay.features.users.domain.repository.contract.IUserBasicDetailRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class UserUpdateBasicDetailsUsecase implements UsecaseContract<Optional<IUserBasicDetailsEntity>, Param<UserBasicDetailsEntity>> {

	private final IUserBasicDetailRepository basicDetailRepository;

	@Override
	public Optional<IUserBasicDetailsEntity> execute(
			Param<UserBasicDetailsEntity> param
	) {
		return basicDetailRepository.updateUserBasicDetailsByUserId( param.getId(), param.getModel() );
	}
}
