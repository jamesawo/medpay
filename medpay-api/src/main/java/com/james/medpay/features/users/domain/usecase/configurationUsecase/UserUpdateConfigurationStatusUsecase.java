
package com.james.medpay.features.users.domain.usecase.configurationUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.users.domain.repository.contract.IUserConfigurationRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class UserUpdateConfigurationStatusUsecase implements UsecaseContract<Optional<Boolean>, Param<Boolean>> {

	private final IUserConfigurationRepository configurationRepository;


	@Override
	public Optional<Boolean> execute(
			Param<Boolean> param
	) {
			return configurationRepository.updateConfigurationStatusByUserId( param.getId(), param.getModel() );
	}
}
