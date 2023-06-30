
package com.james.medpay.features.users.data.interactor.contract;

import com.james.medpay.features.users.data.request.UserAddressRequest;
import com.james.medpay.features.users.domain.entity.enums.AddressTypeEnum;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IUserAddressInteractor {

	ResponseEntity<Optional<UserAddressRequest>> getAddressByUserIdAndAddressType(
			Long userId, AddressTypeEnum addressType
	);

	ResponseEntity<Optional<UserAddressRequest>> getAddressByUserId( Long userId );

	ResponseEntity<Optional<UserAddressRequest>> getEmergencyAddressByUserId( Long userId );

	ResponseEntity<Optional<UserAddressRequest>> updateAddressByUserIdAndAddressType(
			Long userId, UserAddressRequest address, AddressTypeEnum addressType
	);

	ResponseEntity<Optional<UserAddressRequest>> updateFirstAddressByUserId( Long userId, UserAddressRequest request );

	ResponseEntity<Optional<UserAddressRequest>> updateEmergencyAddressByUserId(
			Long userId, UserAddressRequest emergencyAddress
	);

	ResponseEntity<Optional<Boolean>> setAddressOrEmergencyAddressIsVerifiedStatusByUserIdAndAddressType(
			Long userId, boolean isVerified, AddressTypeEnum addressType
	);

	ResponseEntity<Optional<Boolean>> setAddressIsVerifiedStatusByUserId( Long userId, boolean isVerified );

	ResponseEntity<Optional<Boolean>> setEmergencyAddressIsVerifiedStatusByUserId( Long userId, boolean isVerified );

}
