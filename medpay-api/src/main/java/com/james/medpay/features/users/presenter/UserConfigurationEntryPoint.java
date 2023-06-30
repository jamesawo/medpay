package com.james.medpay.features.users.presenter;

import com.james.medpay.features.users.data.interactor.implementation.UserConfigurationInteractor;
import com.james.medpay.features.users.data.request.UserConfigurationRequest;
import com.james.medpay.features.users.domain.entity.enums.MinOrMaxTypeEnum;
import com.james.medpay.features.users.domain.entity.enums.UserLimitTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static com.james.medpay.core.constant.Routes.API_PREFIX;

@RestController
@RequestMapping( value = API_PREFIX + "/user-configuration" )
@RequiredArgsConstructor
public class UserConfigurationEntryPoint {

	private final UserConfigurationInteractor interactor;


	@GetMapping( value = "{userId}" )
	public ResponseEntity<Optional<UserConfigurationRequest>> getByUserId(
			@PathVariable( value = "userId" ) Long userId
	) {
		return this.interactor.getConfigurationByUserId( userId );
	}

	@PutMapping( value = "/update/{userId}" )
	public ResponseEntity<Optional<UserConfigurationRequest>> updateConfigurationByUserId(
			@PathVariable( value = "userId" ) Long userId,
			@Valid @RequestBody UserConfigurationRequest configuration
	) {
		return interactor.updateConfigurationByUserId( userId, configuration );
	}


	@PatchMapping( value = "/status/{userId}" )
	public ResponseEntity<Optional<Boolean>> updateConfigurationStatusByUserId(
			@PathVariable( value = "userId" ) Long userId,
			@RequestParam( value = "status" ) boolean status
	) {
		return this.interactor.updateConfigurationStatusByUserId( userId, status );
	}


	@PatchMapping( value = "/limittype/{userId}" )
	public ResponseEntity<Optional<Boolean>> updateLimitTypeByUserId(
			@PathVariable( value = "userId" ) Long userId,
			@RequestParam( value = "limit" ) UserLimitTypeEnum limit
	) {
		return this.interactor.updateLimitTypeByUserId( userId, limit );
	}

	@PatchMapping( value = "/limit/{userId}" )
	public ResponseEntity<Optional<Boolean>> updateMaxOrMinLimitByUserId(
			@PathVariable( value = "userId" ) Long userId,
			@RequestParam( value = "type" ) MinOrMaxTypeEnum type,
			@RequestParam( value = "value" ) int value
	) {
		return this.interactor.updateMinOrMaxLimitByUserId( userId, value, type );
	}

}
