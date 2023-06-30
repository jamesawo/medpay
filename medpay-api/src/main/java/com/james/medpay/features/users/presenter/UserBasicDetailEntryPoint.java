package com.james.medpay.features.users.presenter;

import com.james.medpay.features.users.data.interactor.implementation.UserBasicDetailsInteractor;
import com.james.medpay.features.users.data.request.UserBasicDetailsRequest;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.james.medpay.core.constant.Routes.API_PREFIX;

@RestController
@RequestMapping( value = API_PREFIX + "/user-details" )
@RequiredArgsConstructor
public class UserBasicDetailEntryPoint {
	private final UserBasicDetailsInteractor interactor;

	@GetMapping(value = "/{userId}")
	public ResponseEntity< Optional< UserBasicDetailsRequest > > getUserBasicDetailsByUserId(
			@PathVariable( value = "userId") Long userId) {
		return this.interactor.getUserBasicDetailsByUserId(userId);
	}

	@PutMapping(value = "/{userId}")
	public ResponseEntity<Optional<UserBasicDetailsRequest>> updateUserBasicDetailsByUserId(
			@PathVariable( value = "userId") Long userId,
			@Valid @RequestBody UserBasicDetailsRequest basicDetails) {
		return this.interactor.updateUserBasicDetailsByUserId(userId,basicDetails);
	}

}
