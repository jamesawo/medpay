package com.james.medpay.features.users.data.interactor.contract;

import com.james.medpay.features.users.data.request.UserRequest;
import com.james.medpay.features.users.data.request.UserSearchRequest;
import com.james.medpay.features.users.domain.entity.enums.UserTypeEnum;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IUserEInteractor {
	
	ResponseEntity<UserRequest> createUser( UserRequest user );

	ResponseEntity<Optional<UserRequest>> getUserById( Long id, boolean trim );

	ResponseEntity<Optional<Boolean>> updateUserIsEnabledStatusById( Long id, boolean status );

	ResponseEntity<Optional<Boolean>> updateUserExpiryDateById( Long id, LocalDate date );

	ResponseEntity<Optional<Boolean>> updateUserTypeById( Long id, UserTypeEnum userType );

	ResponseEntity<List<UserRequest>> getAllUsers(boolean trim );

	//ResponseEntity<Page<UserRequest>> getUserPaginate();

	ResponseEntity<List<UserRequest>> searchByRequestCriteria( UserSearchRequest request );

	ResponseEntity<Optional<UserRequest>> updateAddressAndLimit(UserRequest request);

	ResponseEntity<List<UserRequest>> searchByTerm( String term );
}
