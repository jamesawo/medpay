package com.james.medpay.features.users.presenter;

import com.james.medpay.features.users.data.interactor.implementation.UserAddressInteractor;
import com.james.medpay.features.users.data.request.UserAddressRequest;
import com.james.medpay.features.users.domain.entity.enums.AddressTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static com.james.medpay.core.constant.Routes.API_PREFIX;

@RestController
@RequestMapping( value = API_PREFIX + "/user-address" )
@RequiredArgsConstructor
public class UserAddressEntryPoint {
	private final UserAddressInteractor addressInteractor;

	@GetMapping( value = "/{userId}" )
	public ResponseEntity<Optional<UserAddressRequest>> getAddressByUserId(
			@PathVariable( value = "userId" ) Long userId,
			@RequestParam( value = "type", required = false, defaultValue = "FIRST" ) AddressTypeEnum addressType
	) {
		return this.addressInteractor.getAddressByUserIdAndAddressType( userId, addressType );
	}

	@PutMapping( "/update/{userId}" )
	public ResponseEntity<Optional<UserAddressRequest>> updateAddressByUserId(
			@PathVariable( value = "userId" ) Long userId,
			@Valid @RequestBody UserAddressRequest address,
			@RequestParam( value = "type", required = false, defaultValue = "FIRST" ) AddressTypeEnum addressType
	) {
		return addressInteractor.updateAddressByUserIdAndAddressType( userId, address, addressType );
	}

	@PatchMapping( value = "/status/{userId}" )
	public ResponseEntity<Optional<Boolean>> setAddressIsVerifiedStatusByUserId(
			@PathVariable( value = "userId" ) Long userId,
			@RequestParam( value = "isVerified" ) boolean isVerified,
			@RequestParam( value = "type", required = false, defaultValue = "FIRST" ) AddressTypeEnum addressType
	) {
		return this.addressInteractor
				.setAddressOrEmergencyAddressIsVerifiedStatusByUserIdAndAddressType( userId, isVerified, addressType
				);
	}
}
