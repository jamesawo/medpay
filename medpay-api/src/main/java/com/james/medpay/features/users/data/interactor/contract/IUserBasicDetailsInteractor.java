
package com.james.medpay.features.users.data.interactor.contract;

import com.james.medpay.features.users.data.request.UserBasicDetailsRequest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IUserBasicDetailsInteractor  {

	ResponseEntity<Optional<UserBasicDetailsRequest>> getUserBasicDetailsByUserId( Long userId );

	ResponseEntity<Optional<UserBasicDetailsRequest>> updateUserBasicDetailsByUserId(
			Long userId, UserBasicDetailsRequest basicDetails
	);

}
