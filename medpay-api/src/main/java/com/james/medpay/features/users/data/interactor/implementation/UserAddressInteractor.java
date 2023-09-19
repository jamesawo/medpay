
package com.james.medpay.features.users.data.interactor.implementation;

import com.james.medpay.core.annotation.Interactor;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.users.data.interactor.contract.IUserAddressInteractor;
import com.james.medpay.features.users.data.request.UserAddressRequest;
import com.james.medpay.features.users.domain.entity.UserAddressEntity;
import com.james.medpay.features.users.domain.entity.contract.IUserAddressEntity;
import com.james.medpay.features.users.domain.entity.enums.AddressTypeEnum;
import com.james.medpay.features.users.domain.usecase.addressUsecase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.springframework.http.ResponseEntity.ok;

@Interactor
@RequiredArgsConstructor
public class UserAddressInteractor implements IUserAddressInteractor {
	private final UserUpdateAddressUsecase updateAddressUsecase;
	private final UserUpdateEmergencyAddressUsecase updateEmergencyAddressUsecase;
	private final UserGetAddressByUserUsecase getAddressByUserUsecase;
	private final UserGetEmergencyAddressByUserUsecase getEmergencyAddressByUserUsecase;
	private final UserSetAddressVerifiedStatusUsecase setAddressVerifiedStatusUsecase;
	private final UserSetEmergencyAddressVerifiedStatusUsecase setEmergencyAddressVerifiedStatusUsecase;

	@Override
	public ResponseEntity<Optional<UserAddressRequest>> getAddressByUserIdAndAddressType(
			Long userId, AddressTypeEnum addressType
	) {
		switch ( addressType ) {
			case FIRST:
				return getAddressByUserId( userId );
			case EMERGENCY:
				return getEmergencyAddressByUserId( userId );
			default:
				return ok().body( empty() );
		}
	}

	@Override
	public ResponseEntity<Optional<UserAddressRequest>> getAddressByUserId( Long userId ) {
		Optional<IUserAddressEntity> optional = this.getAddressByUserUsecase.execute( userId );
		UserAddressRequest request = new UserAddressRequest();
		return ok().body( optional.map(
				addressEntity -> request.toRequest( ( UserAddressEntity ) addressEntity ) ) );
	}

	@Override
	public ResponseEntity<Optional<UserAddressRequest>> getEmergencyAddressByUserId( Long userId ) {
		Optional<IUserAddressEntity> optional = this.getEmergencyAddressByUserUsecase.execute( userId );
		UserAddressRequest request = new UserAddressRequest();
		return ok().body( optional.map(
				addressEntity -> request.toRequest( ( UserAddressEntity ) addressEntity ) ) );
	}

	@Override
	public ResponseEntity<Optional<UserAddressRequest>> updateAddressByUserIdAndAddressType(
			Long userId, UserAddressRequest request, AddressTypeEnum address
	) {
		switch ( address ) {
			case FIRST:
				return updateFirstAddressByUserId( userId, request );
			case EMERGENCY:
				return updateEmergencyAddressByUserId( userId, request );
			default:
				return ResponseEntity.ok( empty() );
		}
	}

	@Override
	public ResponseEntity<Optional<UserAddressRequest>> updateFirstAddressByUserId(
			Long userId, UserAddressRequest request
	) {
		Param<UserAddressEntity> param = new Param<>( userId, request.toEntity( request ) );
		Optional<IUserAddressEntity> optional = this.updateAddressUsecase.execute( param );

		return ok().body(
				optional.map( addressEntity -> request.toRequest(
						( UserAddressEntity ) addressEntity ) )
		);
	}

	@Override
	public ResponseEntity<Optional<UserAddressRequest>> updateEmergencyAddressByUserId(
			Long userId, UserAddressRequest emergencyAddress
	) {
		Optional<IUserAddressEntity> optional = this.updateEmergencyAddressUsecase
				.execute( new Param<>( userId, emergencyAddress.toEntity( emergencyAddress ) ) );
		return ok().body(
				optional.map( addressEntity -> emergencyAddress.toRequest(
						( UserAddressEntity ) addressEntity ) )
		);
	}

	@Override
	public ResponseEntity<Optional<Boolean>> setAddressOrEmergencyAddressIsVerifiedStatusByUserIdAndAddressType(
			Long userId, boolean isVerified, AddressTypeEnum addressType
	) {
		switch ( addressType ) {
			case EMERGENCY:
				return this.setEmergencyAddressIsVerifiedStatusByUserId( userId, isVerified );
			case FIRST:
				return this.setAddressIsVerifiedStatusByUserId( userId, isVerified );
			default:
				return ResponseEntity.ok( empty() );
		}
	}

	@Override
	public ResponseEntity<Optional<Boolean>> setAddressIsVerifiedStatusByUserId( Long userId, boolean isVerified ) {
		Optional<Boolean> optional = this.setAddressVerifiedStatusUsecase.execute( new Param<>( userId, isVerified ) );
		return ok().body( optional );
	}

	@Override
	public ResponseEntity<Optional<Boolean>> setEmergencyAddressIsVerifiedStatusByUserId(
			Long userId, boolean isVerified
	) {
		Optional<Boolean> optional = this.setEmergencyAddressVerifiedStatusUsecase.execute(
				new Param<>( userId, isVerified ) );
		return ok().body( optional );
	}
}
