
package com.james.medpay.features.users.data.interactor.implementation;

import com.james.medpay.core.annotation.Interactor;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.users.data.interactor.contract.IUserBasicDetailsInteractor;
import com.james.medpay.features.users.data.request.UserBasicDetailsRequest;
import com.james.medpay.features.users.domain.entity.UserBasicDetailsEntity;
import com.james.medpay.features.users.domain.entity.contract.IUserBasicDetailsEntity;
import com.james.medpay.features.users.domain.usecase.basicDetailsUsecase.UserGetBasicDetailsUsecase;
import com.james.medpay.features.users.domain.usecase.basicDetailsUsecase.UserUpdateBasicDetailsUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@Interactor
@RequiredArgsConstructor
public class UserBasicDetailsInteractor implements IUserBasicDetailsInteractor {

	private final UserGetBasicDetailsUsecase getBasicDetailsUsecase;
	private final UserUpdateBasicDetailsUsecase updateBasicDetailsUsecase;

	@Override
	public ResponseEntity<Optional<UserBasicDetailsRequest>> getUserBasicDetailsByUserId( Long userId ) {
		Optional<IUserBasicDetailsEntity> optional = this.getBasicDetailsUsecase.execute( userId );
		UserBasicDetailsRequest request = new UserBasicDetailsRequest();
		return ResponseEntity.ok().body( optional.map( request::toRequest ) );
	}

	@Override
	public ResponseEntity<Optional<UserBasicDetailsRequest>> updateUserBasicDetailsByUserId(
			Long userId, UserBasicDetailsRequest basicDetails
	) {
		UserBasicDetailsRequest request = new UserBasicDetailsRequest();
		UserBasicDetailsEntity basicDetailsEntity = ( UserBasicDetailsEntity ) request.toEntity( basicDetails );
		Optional<IUserBasicDetailsEntity> optional = this.updateBasicDetailsUsecase.execute(
				new Param<>( userId, basicDetailsEntity ) );

		return ResponseEntity.ok().body( optional.map( request::toRequest ) );
	}
}
