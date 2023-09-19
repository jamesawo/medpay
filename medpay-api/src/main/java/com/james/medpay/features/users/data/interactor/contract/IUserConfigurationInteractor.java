package com.james.medpay.features.users.data.interactor.contract;

import com.james.medpay.features.users.data.request.UserConfigurationRequest;
import com.james.medpay.features.users.domain.entity.enums.MinOrMaxTypeEnum;
import com.james.medpay.features.users.domain.entity.enums.UserLimitTypeEnum;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IUserConfigurationInteractor {
	ResponseEntity<Optional<UserConfigurationRequest>> getConfigurationByUserId( Long userId );

	ResponseEntity<Optional<UserConfigurationRequest>> updateConfigurationByUserId(
			Long userId, UserConfigurationRequest configuration
	);

	ResponseEntity<Optional<Boolean>> updateConfigurationStatusByUserId( Long userId, boolean status );

	ResponseEntity<Optional<Boolean>> updateLimitTypeByUserId( Long userId, UserLimitTypeEnum type );

	ResponseEntity<Optional<Boolean>> updateMinOrMaxLimitByUserId( Long userId, int value, MinOrMaxTypeEnum type );

	ResponseEntity<Optional<Boolean>> updateMaxLimitByUserId( Long userId, int maxLimit );

	ResponseEntity<Optional<Boolean>> updateMinLimitByUserId( Long userId, int minLimit );

}
