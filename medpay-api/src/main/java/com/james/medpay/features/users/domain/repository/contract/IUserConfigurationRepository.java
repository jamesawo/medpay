
package com.james.medpay.features.users.domain.repository.contract;

import com.james.medpay.features.users.domain.entity.UserConfigurationEntity;
import com.james.medpay.features.users.domain.entity.contract.IUserConfigurationEntity;
import com.james.medpay.features.users.domain.entity.enums.UserLimitTypeEnum;

import java.util.Optional;

public interface IUserConfigurationRepository {

	Optional<IUserConfigurationEntity> getConfigurationByUserId( Long userId );

	Optional<IUserConfigurationEntity> updateConfigurationByUserId(
			Long userId, UserConfigurationEntity configuration
	);

	Optional<Boolean> updateConfigurationStatusByUserId( Long userId, boolean status );

	Optional<Boolean> updateLimitTypeByUserId( Long userId, UserLimitTypeEnum type );

	Optional<Boolean> updateMaxLimitByUserId( Long userId, int maxLimit );

	Optional<Boolean> updateMinLimitByUserId( Long userId, int minLimit );

}
