
package com.james.medpay.features.users.domain.usecase.configurationUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.users.domain.entity.UserConfigurationEntity;
import com.james.medpay.features.users.domain.entity.contract.IUserConfigurationEntity;
import com.james.medpay.features.users.domain.repository.contract.IUserConfigurationRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class UserUpdateConfigurationUsecase implements UsecaseContract<Optional<IUserConfigurationEntity>, Param<UserConfigurationEntity>> {

	private final IUserConfigurationRepository configurationRepository;


	@Override
	public Optional<IUserConfigurationEntity> execute(
			Param<UserConfigurationEntity> param
	) {
		return configurationRepository.updateConfigurationByUserId( param.getId(), param.getModel() );
	}
}
