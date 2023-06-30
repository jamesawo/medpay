
package com.james.medpay.features.users.domain.usecase.configurationUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.users.domain.entity.contract.IUserConfigurationEntity;
import com.james.medpay.features.users.domain.repository.contract.IUserConfigurationRepository;

import java.util.Optional;

@UseCase
public class UserGetConfigurationUsecase implements UsecaseContract<Optional<IUserConfigurationEntity>, Long> {

	private final IUserConfigurationRepository configurationRepository;

	public UserGetConfigurationUsecase( IUserConfigurationRepository configurationRepository ) {
		this.configurationRepository = configurationRepository;
	}


	@Override
	public Optional<IUserConfigurationEntity> execute(
			Long userId
	) {
		return configurationRepository.getConfigurationByUserId( userId );
	}
}
