package com.james.medpay.features.users.presenter;

import com.james.medpay.features.users.data.interactor.implementation.UserEInteractor;
import com.james.medpay.features.users.data.request.UserRequest;
import com.james.medpay.features.users.data.request.UserSearchRequest;
import com.james.medpay.features.users.domain.entity.enums.UserTypeEnum;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.james.medpay.core.constant.Routes.API_PREFIX;

@RestController
@RequestMapping( value = API_PREFIX + "/user" )
@RequiredArgsConstructor
public class UserEBaseEntryPoint {
	private final UserEInteractor userInteractor;

	@PostMapping( value = "/create" )
	public ResponseEntity< UserRequest > createUser(@Valid @RequestBody UserRequest request) {
		return this.userInteractor.createUser(request);
	}

	@GetMapping( value = "/{userId}" )
	public ResponseEntity< Optional< UserRequest > > getUserById(@RequestParam( value = "trim", required = false, defaultValue = "true" ) boolean trim, @PathVariable( value = "userId" ) Long userId) {
		return this.userInteractor.getUserById(userId, trim);
	}

	@PatchMapping( value = "/update-status/{userId}" )
	public ResponseEntity< Optional< Boolean > > updateUserIsEnabledStatusById(@PathVariable( value = "userId" ) Long userId, @RequestParam( value = "status" ) boolean status) {
		return this.userInteractor.updateUserIsEnabledStatusById(userId, status);
	}

	@PatchMapping( value = "/update-date/{userId}" )
	public ResponseEntity< Optional< Boolean > > updateUserExpiryDateById(@PathVariable( value = "userId" ) Long userId, @RequestParam( value = "date" ) String date) {
		LocalDate localDate = LocalDate.parse(date);
		return this.userInteractor.updateUserExpiryDateById(userId, localDate);
	}

	@PatchMapping( value = "/update-type/{userId}" )
	public ResponseEntity< Optional< Boolean > > updateUserTypeById(@PathVariable( value = "userId" ) Long userId, @RequestParam( value = "type" ) UserTypeEnum type) {
		return this.userInteractor.updateUserTypeById(userId, type);
	}

	@GetMapping( value = "/all" )
	public ResponseEntity< List< UserRequest > > getAllUsers(@RequestParam( value = "trim", required = false, defaultValue = "true" ) boolean trim) {
		return this.userInteractor.getAllUsers(trim);
	}

	@PostMapping( value = "/search-by-request" )
	public ResponseEntity< List< UserRequest > > getUserPaginate(@RequestBody UserSearchRequest request) {
		return this.userInteractor.searchByRequestCriteria(request);
	}

	@PutMapping( value = "/update/address-limit" )
	public ResponseEntity< Optional< UserRequest > > updateAddressAndLimitType(@Valid @RequestBody UserRequest userRequest) {
		return this.userInteractor.updateAddressAndLimit(userRequest);
	}

	@GetMapping( value = "/search-by-term" )
	public ResponseEntity< List< UserRequest > > searchByTerm(@RequestParam( value = "term" ) String term) {
		return this.userInteractor.searchByTerm(term);
	}

}
