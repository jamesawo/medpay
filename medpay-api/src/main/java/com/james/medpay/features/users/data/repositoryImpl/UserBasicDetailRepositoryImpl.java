package com.james.medpay.features.users.data.repositoryImpl;

import com.james.medpay.core.annotation.RepositoryImpl;
import com.james.medpay.features.users.domain.entity.UserBasicDetailsEntity;
import com.james.medpay.features.users.domain.entity.UserEntity;
import com.james.medpay.features.users.domain.entity.contract.IUserBasicDetailsEntity;
import com.james.medpay.features.users.domain.repository.contract.IUserBasicDetailRepository;
import com.james.medpay.features.users.domain.repository.dataRepository.IUserBasicDetailsDataRepository;
import com.james.medpay.features.users.domain.repository.dataRepository.IUserDataRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RepositoryImpl
@RequiredArgsConstructor
public class UserBasicDetailRepositoryImpl implements IUserBasicDetailRepository {

	private final IUserDataRepository userDataRepository;
	private final IUserBasicDetailsDataRepository basicDetailsDataRepository;

	@Override
	public Optional<IUserBasicDetailsEntity> getUserBasicDetailsByUserId( Long userId ) {
		Optional<UserEntity> optional = this._findUserFromDataRepository( userId );
		return optional.map( UserEntity::getBasicDetails );
	}

	@Override
	public Optional<IUserBasicDetailsEntity> updateUserBasicDetailsByUserId(
			Long userId, UserBasicDetailsEntity basicDetails
	) {
		Optional<UserEntity> optional = this._findUserFromDataRepository( userId );
		return optional.map( user -> {
			basicDetails.setId( user.getBasicDetails().getId() );
			return basicDetailsDataRepository.save( basicDetails );
		} );
	}

	private Optional<UserEntity> _findUserFromDataRepository( Long userId ) {
		return this.userDataRepository.findById( userId );
	}
}
