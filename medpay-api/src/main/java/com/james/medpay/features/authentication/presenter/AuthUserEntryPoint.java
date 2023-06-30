package com.james.medpay.features.authentication.presenter;

import com.james.medpay.features.authentication.data.interactor.contract.IAuthUserInteractor;
import com.james.medpay.features.authentication.data.request.PasswordResetRequest;
import com.james.medpay.features.authentication.data.request.RoleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.james.medpay.core.constant.Routes.API_PREFIX;

@RestController
@RequestMapping( value = API_PREFIX + "/auth-user" )
@RequiredArgsConstructor
public class AuthUserEntryPoint {

	private final IAuthUserInteractor interactor;

	@PostMapping( value = "/{userId}/assign-roles" )
	ResponseEntity<Optional<Boolean>> assignRolesToUser(
			@PathVariable Long userId,
			@RequestBody List<RoleRequest> request
	) {
		return this.interactor.assignRolesToUser( userId, request );
	}

	@GetMapping( value = "/{userId}" )
	ResponseEntity<List<RoleRequest>> getUserRoles(
			@PathVariable Long userId
	) {
		return this.interactor.getUserRoles( userId );
	}

	@PostMapping(value = "/reset-password")
	public ResponseEntity<Optional<Boolean>> resetUserPassword(@Valid @RequestBody PasswordResetRequest request ){
		return this.interactor.resetUserPassword(request);
	}

}
