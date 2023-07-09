package com.james.medpay.features.authentication.data.interactor.implementation;

import com.james.medpay.core.annotation.Interactor;
import com.james.medpay.features.authentication.data.interactor.contract.IAuthRegisterInteractor;
import com.james.medpay.features.authentication.data.request.RegisterRequest;
import com.james.medpay.features.users.data.interactor.implementation.UserEInteractor;
import com.james.medpay.features.users.data.request.UserBasicDetailsRequest;
import com.james.medpay.features.users.data.request.UserRequest;
import com.james.medpay.features.users.domain.entity.contract.IUserEntity;
import com.james.medpay.features.users.domain.entity.enums.UserTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import static org.springframework.http.ResponseEntity.*;

@Interactor
@RequiredArgsConstructor
public class AuthRegisterInteractorImpl implements IAuthRegisterInteractor {

	private final UserEInteractor userEInteractor;


	@Override
	public ResponseEntity<RegisterRequest> register(RegisterRequest request) {
		UserRequest userRequest = this.mapToUserRequestFromRegisterRequest(request);
		IUserEntity user = this.userEInteractor.register(userRequest);
		setRegisterRequest(user, request);
		return ok().body(request);
	}

	private void setRegisterRequest(IUserEntity user, RegisterRequest request ){
		request.setId(user.getId());
		request.setPhone(user.getPhoneNumber());
		request.setType(user.getUserTypeEnum());
		request.setIsEnabled(user.getIsEnabled());
		request.setUsername(user.getNickName());
		request.setUuid(user.getUuid());
		request.setEmail(user.getEmail());
	}


	private UserRequest mapToUserRequestFromRegisterRequest(RegisterRequest request) {
		UserRequest userRequest = new UserRequest();
		userRequest.setEmail(request.getEmail());
		userRequest.setUserTypeEnum(UserTypeEnum.REGULAR_USER);
		userRequest.setPassword(request.getPassword());
		String name = request.getName();
		if (name.contains(" ")){
			String[] names = name.split(" ");
			userRequest.setNickName(names[0]);
		} else {
			userRequest.setNickName(name);
		}
		userRequest.setBasicDetails(getUserBasicDetails(request));
		return userRequest;
	}

	private UserBasicDetailsRequest getUserBasicDetails(RegisterRequest request){
		UserBasicDetailsRequest basicDetails = new UserBasicDetailsRequest();
		basicDetails.setPhoneNumber(request.getPhone());

		if ( StringUtils.containsWhitespace(request.getName())){
			String[] names = request.getName().split(" ");
			basicDetails.setFirstName(names[0]);
			basicDetails.setLastName(names[1]);
		}else {
			basicDetails.setFirstName(request.getName());
		}

		return basicDetails;

	}

}
