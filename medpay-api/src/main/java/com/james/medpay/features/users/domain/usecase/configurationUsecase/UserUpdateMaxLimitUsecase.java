
package com.james.medpay.features.users.domain.usecase.configurationUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.users.domain.repository.contract.IUserConfigurationRepository;

import java.util.Optional;

@UseCase
public class UserUpdateMaxLimitUsecase implements UsecaseContract<Optional<Boolean>, Param<Integer>> {

	private final IUserConfigurationRepository configurationRepository;

	public UserUpdateMaxLimitUsecase(IUserConfigurationRepository configurationRepository) {
		this.configurationRepository = configurationRepository;
	}


	@Override
	public Optional<Boolean> execute(
			Param<Integer> param
	) {
		return configurationRepository.updateMaxLimitByUserId( param.getId(), param.getModel() );
	}
}
