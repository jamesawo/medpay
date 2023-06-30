
package com.james.medpay.features.users.data.repositoryImpl;

import com.james.medpay.core.annotation.RepositoryImpl;
import com.james.medpay.core.exception.InvalidRequestException;
import com.james.medpay.features.authentication.domain.entity.Role;
import com.james.medpay.features.users.domain.entity.UserAddressEntity;
import com.james.medpay.features.users.domain.entity.UserBasicDetailsEntity;
import com.james.medpay.features.users.domain.entity.UserConfigurationEntity;
import com.james.medpay.features.users.domain.entity.UserEntity;
import com.james.medpay.features.users.domain.entity.contract.IUserEntity;
import com.james.medpay.features.users.domain.entity.enums.TrimPatternEnum;
import com.james.medpay.features.users.domain.entity.enums.UserTypeEnum;
import com.james.medpay.features.users.domain.repository.contract.IUserEntityRepository;
import com.james.medpay.features.users.domain.repository.dataRepository.IUserAddressDataRepository;
import com.james.medpay.features.users.domain.repository.dataRepository.IUserBasicDetailsDataRepository;
import com.james.medpay.features.users.domain.repository.dataRepository.IUserConfigurationDataRepository;
import com.james.medpay.features.users.domain.repository.dataRepository.IUserDataRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@RepositoryImpl
@RequiredArgsConstructor
public class UserEntityRepositoryImpl implements IUserEntityRepository {
	private final IUserDataRepository userDataRepository;
	private final IUserAddressDataRepository addressDataRepository;
	private final IUserBasicDetailsDataRepository basicDetailsDataRepository;
	private final IUserConfigurationDataRepository configurationDataRepository;


	@Override
	@Transactional
	public IUserEntity createUser( UserEntity user ) {
		this._savePartialUserEntity( user );
		return userDataRepository.save( user );
	}

	private void _savePartialUserEntity( UserEntity user ) {
		user.setAddress( this.addressDataRepository.save( this._getPartialUserAddress( user ) ) );
		user.setBasicDetails( this.basicDetailsDataRepository.save( this._getPartialUserBasic( user ) ) );
		user.setConfiguration( this.configurationDataRepository.save( this._getPartialUserConfiguration( user ) ) );
		user.setEmergencyAddress( this.addressDataRepository.save( this._getPartialUserEmergencyAddress( user ) ) );
		if ( isEmpty( user.getHospital() ) || isEmpty( user.getHospital().getId() ) ) {
			user.setHospital( null );
		}
	}

	private UserAddressEntity _getPartialUserAddress( UserEntity user ) {
		return user.getAddress() != null ? user.getAddress() : new UserAddressEntity();
	}

	private UserBasicDetailsEntity _getPartialUserBasic( UserEntity user ) {
		return user.getBasicDetails() != null ? user.getBasicDetails() : new UserBasicDetailsEntity();
	}

	private UserConfigurationEntity _getPartialUserConfiguration( UserEntity user ) {
		return user.getConfiguration() != null ? user.getConfiguration() : new UserConfigurationEntity();
	}

	private UserAddressEntity _getPartialUserEmergencyAddress( UserEntity user ) {
		return user.getEmergencyAddress() != null ? user.getEmergencyAddress() : new UserAddressEntity();
	}

	@Override
	public Optional<IUserEntity> getUserById( Long id ) {
		Optional<UserEntity> optional = _findUserFromDataRepository( id );
		return optional.map( user -> user );
	}

	@Override
	public Optional<Boolean> updateUserIsEnabledStatusById( Long id, boolean status ) {
		Optional<UserEntity> optional = this._findUserFromDataRepository( id );
		return of( optional.map( user -> {
			user.setIsEnabled( status );
			userDataRepository.save( user );
			return true;
		} ).orElseGet( () -> false ) );
	}

	@Override
	public Optional<Boolean> updateUserExpiryDateById( Long id, LocalDate date ) {
		Optional<UserEntity> optional = this._findUserFromDataRepository( id );
		return of( optional.map( user -> {
			user.setExpiryDate( date );
			this.userDataRepository.save( user );
			return true;
		} ).orElseGet( () -> false ) );
	}

	@Override
	public Optional<Boolean> updateUserTypeById(
			Long id, UserTypeEnum userType
	) {
		Optional<UserEntity> optional = this._findUserFromDataRepository( id );
		return of( optional.map( user -> {
			user.setUserTypeEnum( userType );
			this.userDataRepository.save( user );
			return true;
		} ).orElseGet( () -> false ) );
	}

	@Override
	public List<IUserEntity> getAllUsers() {
		List<UserEntity> all = this.userDataRepository.findAll();
		return new ArrayList<>( all );
	}

	@Override
	public Page<IUserEntity> getUserPaginate() {
		return null;
	}

	@Override
	public Optional<IUserEntity> getByEmail( String email ) {
		Optional<UserEntity> optional = this.userDataRepository.findByEmailIgnoreCase( email );
		return optional.map( user -> user );
	}

	@Override
	public List<IUserEntity> getBySpecificationCriteria( Specification<UserEntity> specification ) {
		List<UserEntity> result = this.userDataRepository.findAll( specification );
		result.forEach( user -> user.trim( TrimPatternEnum.PARTIAL ) );
		return new ArrayList<>( result );
	}

	@Override
	public Optional<IUserEntity> updateAddressAndLimit( UserEntity user ) {
		Optional<UserEntity> optional = this._findUserFromDataRepository( user.getId() );
		return optional.map( userEntity -> {
			this._updateLimit( userEntity, user );
			this._updateAddressAndEmergencyAddress( userEntity, user );
			return userEntity;
		} );
	}

	@Override
	public Optional<Boolean> updateUserRoles( Long userId, Collection<Role> collection ) {
		Optional<UserEntity> optional = this.userDataRepository.findById( userId );
		return optional.map( user1 -> {
			user1.setRoles( collection );
			this.userDataRepository.save( user1 );
			return of( true );
		} ).orElse( of( false ) );
	}

	@Override
	public Collection<Role> getUserRoles( Long userId ) {
		Optional<UserEntity> optional = this.userDataRepository.findById( userId );
		return optional.map( UserEntity::getRoles ).orElse( new ArrayList<>() );
	}

	@Override
	public Optional<Boolean> resetPassword( UserEntity user, String password ) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword( encoder.encode( password ) );
		this.userDataRepository.save( user );
		return of(true);
	}


	private void _updateAddressAndEmergencyAddress( UserEntity existingUser, UserEntity toUpdate ) {
		this._checkAddressAndEmergencyIsNotNull( toUpdate );
		this._updateAddressOnly( existingUser, toUpdate );
		this._updateEmergencyAddressOnly( existingUser, toUpdate );
	}

	private void _updateEmergencyAddressOnly( UserEntity existingUser, UserEntity toUpdate ) {
		UserAddressEntity emergencyAddress = existingUser.getEmergencyAddress();
		emergencyAddress.setCurrentPlaceOfResidence( toUpdate.getEmergencyAddress().getCurrentPlaceOfResidence() );
		UserAddressEntity emergencyAddressEntity = this.addressDataRepository.save( emergencyAddress );
		existingUser.setEmergencyAddress( emergencyAddressEntity );
	}

	private void _updateAddressOnly( UserEntity existingUser, UserEntity toUpdate ) {
		UserAddressEntity address = existingUser.getAddress();
		address.setCurrentPlaceOfResidence( toUpdate.getAddress().getCurrentPlaceOfResidence() );
		UserAddressEntity userAddressEntity = this.addressDataRepository.save( address );
		existingUser.setAddress( userAddressEntity );
	}

	private void _checkAddressAndEmergencyIsNotNull( UserEntity user ) {
		if ( isEmpty( user.getAddress() ) || isEmpty( user.getEmergencyAddress() ) ) {
			throw new InvalidRequestException( "User's Address and/or Emergency address is required" );
		}

		if ( isEmpty( user.getAddress().getCurrentPlaceOfResidence() ) ) {
			throw new InvalidRequestException( "User's Address current place of residence is required" );
		}

		if ( isEmpty( user.getEmergencyAddress().getCurrentPlaceOfResidence() ) ) {
			throw new InvalidRequestException( "User's Emergency Address is required" );
		}
	}

	private void _updateLimit( UserEntity user, UserEntity toUpdate ) {
		UserConfigurationEntity configuration = user.getConfiguration();
		configuration.setLimitType( toUpdate.getConfiguration().getLimitType() );
		configuration.setMinLimit( toUpdate.getConfiguration().getMinLimit() );
		configuration.setMaxLimit( toUpdate.getConfiguration().getMaxLimit() );
		UserConfigurationEntity configurationEntity = this.configurationDataRepository.save( configuration );
		user.setConfiguration( configurationEntity );
	}

	private Optional<UserEntity> _findUserFromDataRepository( Long userId ) {
		return this.userDataRepository.findById( userId );
	}
}
