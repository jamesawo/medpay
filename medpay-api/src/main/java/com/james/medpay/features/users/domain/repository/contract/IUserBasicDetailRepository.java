package com.james.medpay.features.users.domain.repository.contract;

import com.james.medpay.features.users.domain.entity.UserBasicDetailsEntity;
import com.james.medpay.features.users.domain.entity.contract.IUserBasicDetailsEntity;

import java.util.Optional;

public interface IUserBasicDetailRepository {

	Optional<IUserBasicDetailsEntity> getUserBasicDetailsByUserId( Long userId );

	Optional<IUserBasicDetailsEntity> updateUserBasicDetailsByUserId(
			Long userId, UserBasicDetailsEntity basicDetails
	);
}
