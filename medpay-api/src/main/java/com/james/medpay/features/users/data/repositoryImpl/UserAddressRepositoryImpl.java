
package com.james.medpay.features.users.data.repositoryImpl;

import com.james.medpay.core.annotation.RepositoryImpl;
import com.james.medpay.features.users.domain.entity.UserAddressEntity;
import com.james.medpay.features.users.domain.entity.UserEntity;
import com.james.medpay.features.users.domain.entity.contract.IUserAddressEntity;
import com.james.medpay.features.users.domain.entity.enums.AddressTypeEnum;
import com.james.medpay.features.users.domain.repository.contract.IUserAddressRepository;
import com.james.medpay.features.users.domain.repository.dataRepository.IUserAddressDataRepository;
import com.james.medpay.features.users.domain.repository.dataRepository.IUserDataRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.james.medpay.features.users.domain.entity.enums.AddressTypeEnum.EMERGENCY;
import static com.james.medpay.features.users.domain.entity.enums.AddressTypeEnum.FIRST;
import static java.util.Optional.of;

@RepositoryImpl
@RequiredArgsConstructor
public class UserAddressRepositoryImpl implements IUserAddressRepository {

	private final IUserAddressDataRepository addressDataRepository;
	private final IUserDataRepository userDataRepository;

	@Override
	public Optional<IUserAddressEntity> getAddressByUserId( Long userId ) {
		return _findUserFromDataRepository( userId ).map( UserEntity::getAddress );
	}

	@Override
	public Optional<IUserAddressEntity> getEmergencyAddressByUserId( Long userId ) {
		return _findUserFromDataRepository( userId ).map( UserEntity::getEmergencyAddress );
	}

	@Override
	public Optional<IUserAddressEntity> updateAddressByUserId( Long userId, UserAddressEntity address ) {
		Optional<UserEntity> optional = _findUserFromDataRepository( userId );
		return optional.map( user -> {
			address.setId( user.getId() );
			return addressDataRepository.save( address );
		} );
	}

	@Override
	public Optional<IUserAddressEntity> updateEmergencyAddressByUserId(
			Long userId, UserAddressEntity emergencyAddress
	) {
		Optional<UserEntity> optional = _findUserFromDataRepository( userId );
		return optional.map( user -> {
			user.setEmergencyAddress( emergencyAddress );
			return addressDataRepository.save( user.getEmergencyAddress() );
		} );
	}

	@Override
	public Optional<Boolean> setAddressIsVerifiedStatusByUserId( Long userId, boolean isVerified ) {
		Optional<UserEntity> optional = _findUserFromDataRepository( userId );
		return optional.map( user -> _setAddressIsVerified( user, isVerified, FIRST ) )
		               .orElseGet( () -> of( false ) );
	}

	private Optional<Boolean> _setAddressIsVerified( UserEntity user, boolean isVerified, AddressTypeEnum type ) {
		user.updateAddressIsVerified( isVerified, type );
		addressDataRepository.save( user.getAddress() );
		return of( true );
	}


	@Override
	public Optional<Boolean> setEmergencyAddressIsVerifiedStatusByUserId( Long userId, boolean isVerified ) {
		Optional<UserEntity> optional = _findUserFromDataRepository( userId );
		return optional.map( user -> _setAddressIsVerified( user, isVerified, EMERGENCY ) )
		               .orElseGet( () -> of( false ) );
	}

	private Optional<UserEntity> _findUserFromDataRepository( Long userId ) {
		return this.userDataRepository.findById( userId );
	}
}
