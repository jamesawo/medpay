
package com.james.medpay.features.users.domain.repository.contract;

import com.james.medpay.features.users.domain.entity.UserAddressEntity;
import com.james.medpay.features.users.domain.entity.contract.IUserAddressEntity;

import java.util.Optional;

public interface IUserAddressRepository {
	/**
	 * Returns Optional because if the userId is invalid,
	 * the UserEntity won't be found hence the UserAddress will not be found too
	 * <p>
	 * Don't throw exception, return Optional.empty()
	 */
	Optional<IUserAddressEntity> getAddressByUserId( Long userId );

	Optional<IUserAddressEntity> getEmergencyAddressByUserId( Long userId );

	Optional<IUserAddressEntity> updateAddressByUserId( Long userId, UserAddressEntity address );

	Optional<IUserAddressEntity> updateEmergencyAddressByUserId( Long userId, UserAddressEntity emergencyAddress );

	Optional<Boolean> setAddressIsVerifiedStatusByUserId( Long userId, boolean isVerified );

	Optional<Boolean> setEmergencyAddressIsVerifiedStatusByUserId( Long userId, boolean isVerified );
}
