
package com.james.medpay.features.users.domain.usecase.configurationUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.users.domain.repository.contract.IUserConfigurationRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class UserUpdateMinLimitUsecase implements UsecaseContract<Optional<Boolean>, Param<Integer>> {

	private final IUserConfigurationRepository configurationRepository;


	@Override
	public Optional<Boolean> execute(
			Param<Integer> param
	) {
		return configurationRepository.updateMinLimitByUserId( param.getId(), param.getModel() );
	}
}
