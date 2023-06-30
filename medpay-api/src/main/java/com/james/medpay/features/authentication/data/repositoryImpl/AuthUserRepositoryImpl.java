package com.james.medpay.features.authentication.data.repositoryImpl;

import com.james.medpay.core.annotation.RepositoryImpl;
import com.james.medpay.core.exception.InvalidRequestException;
import com.james.medpay.core.params.BiParam;
import com.james.medpay.features.authentication.domain.entity.Role;
import com.james.medpay.features.authentication.domain.repository.contract.IAuthRoleRepository;
import com.james.medpay.features.authentication.domain.repository.contract.IAuthUserRepository;
import com.james.medpay.features.users.domain.entity.UserEntity;
import com.james.medpay.features.users.domain.entity.contract.IUserEntity;
import com.james.medpay.features.users.domain.repository.contract.IUserEntityRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;

@RepositoryImpl
@RequiredArgsConstructor
public class AuthUserRepositoryImpl implements IAuthUserRepository {

	private final IUserEntityRepository userEntityRepository;
	private final IAuthRoleRepository roleRepository;


	@Override
	public Optional<Boolean> assignRolesToUser( Long userId, List<Role> list ) {
		Collection<Role> collection = new ArrayList<>();
		list.forEach( role -> this.roleRepository.getById( role.getId() ).ifPresent( e -> collection.add( ( Role ) e ) ) );
		return this.userEntityRepository.updateUserRoles( userId, collection );
	}

	@Override
	public Collection<Role>  getUserRoles( Long userId) {
		return this.userEntityRepository.getUserRoles( userId );
	}

	@Override
	public Optional<Boolean> resetUserPassword(
			Long userId, BiParam<String, String> biParam
	) {
		_checkIfPasswordMatchOrThrow( biParam.getLeft(),  biParam.getRight());
		Optional<IUserEntity> optional = this.userEntityRepository.getUserById( userId );
		return optional.map( iUser -> this.userEntityRepository.resetPassword( ( UserEntity ) iUser, biParam.getLeft()) ).orElse( of( false ) );
	}

	private void _checkIfPasswordMatchOrThrow(String password, String confirmPassword){
		if ( !confirmPassword.equalsIgnoreCase( password ) ) {
			throw new InvalidRequestException("Password and confirm password does not match" );
		}
	}

}
