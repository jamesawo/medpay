
package com.james.medpay.features.users.domain.usecase.configurationUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.users.domain.entity.enums.UserLimitTypeEnum;
import com.james.medpay.features.users.domain.repository.contract.IUserConfigurationRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class UserUpdateLimitTypeUsecase implements UsecaseContract<Optional<Boolean>, Param<UserLimitTypeEnum>> {

	private final IUserConfigurationRepository configurationRepository;


	@Override
	public Optional<Boolean> execute(
			Param<UserLimitTypeEnum> param
	) {
		return configurationRepository.updateLimitTypeByUserId( param.getId(), param.getModel() );
	}
}
