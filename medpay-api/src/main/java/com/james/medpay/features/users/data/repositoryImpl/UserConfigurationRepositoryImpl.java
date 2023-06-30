
package com.james.medpay.features.users.data.repositoryImpl;

import com.james.medpay.core.annotation.RepositoryImpl;
import com.james.medpay.features.users.domain.entity.UserConfigurationEntity;
import com.james.medpay.features.users.domain.entity.UserEntity;
import com.james.medpay.features.users.domain.entity.contract.IUserConfigurationEntity;
import com.james.medpay.features.users.domain.entity.enums.UserLimitTypeEnum;
import com.james.medpay.features.users.domain.repository.contract.IUserConfigurationRepository;
import com.james.medpay.features.users.domain.repository.dataRepository.IUserConfigurationDataRepository;
import com.james.medpay.features.users.domain.repository.dataRepository.IUserDataRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static java.util.Optional.of;

@RepositoryImpl
@RequiredArgsConstructor
public class UserConfigurationRepositoryImpl implements IUserConfigurationRepository {

	private final IUserDataRepository userDataRepository;
	private final IUserConfigurationDataRepository configurationDataRepository;


	@Override
	public Optional<IUserConfigurationEntity> getConfigurationByUserId( Long userId ) {
		Optional<UserEntity> optional = _findUserFromDataRepository( userId );
		return optional.map( UserEntity::getConfiguration );
	}

	@Override
	public Optional<IUserConfigurationEntity> updateConfigurationByUserId(
			Long userId, UserConfigurationEntity configuration
	) {
		Optional<UserEntity> optional = _findUserFromDataRepository( userId );
		return optional.map( user -> {
			configuration.setId( user.getConfiguration().getId() );
			return configurationDataRepository.save( configuration );
		} );
	}

	@Override
	public Optional<Boolean> updateConfigurationStatusByUserId( Long userId, boolean status ) {
		Optional<UserEntity> optional = _findUserFromDataRepository( userId );
		return of( optional.map( user -> {
			user.getConfiguration().setIsEnabled( status );
			configurationDataRepository.save( user.getConfiguration() );
			return true;
		} ).orElseGet( () -> false ) );
	}

	@Override
	public Optional<Boolean> updateLimitTypeByUserId(
			Long userId, UserLimitTypeEnum type
	) {
		Optional<UserEntity> optional = _findUserFromDataRepository( userId );
		return of( optional.map( user -> {
			user.getConfiguration().setLimitType( type );
			configurationDataRepository.save( user.getConfiguration() );
			return true;
		} ).orElseGet( () -> false ) );
	}

	@Override
	public Optional<Boolean> updateMaxLimitByUserId(
			Long userId, int maxLimit
	) {
		Optional<UserEntity> optional = _findUserFromDataRepository( userId );
		return of( optional.map( user -> {
			user.getConfiguration().setMaxLimit( maxLimit );
			configurationDataRepository.save( user.getConfiguration() );
			return true;
		} ).orElseGet( () -> false ) );
	}

	@Override
	public Optional<Boolean> updateMinLimitByUserId(
			Long userId, int minLimit
	) {
		Optional<UserEntity> optional = _findUserFromDataRepository( userId );
		return of( optional.map( user -> {
			user.getConfiguration().setMinLimit( minLimit );
			configurationDataRepository.save( user.getConfiguration() );
			return true;
		} ).orElseGet( () -> false ) );
	}

	private Optional<UserEntity> _findUserFromDataRepository( Long userId ) {
		return this.userDataRepository.findById( userId );
	}
}
