
package com.james.medpay.features.users.domain.usecase.addressUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.users.domain.entity.UserAddressEntity;
import com.james.medpay.features.users.domain.entity.contract.IUserAddressEntity;
import com.james.medpay.features.users.domain.repository.contract.IUserAddressRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class UserUpdateAddressUsecase implements UsecaseContract<Optional<IUserAddressEntity>, Param<UserAddressEntity>> {
	private final IUserAddressRepository userAddressRepository;

	@Override
	public Optional<IUserAddressEntity> execute( Param<UserAddressEntity> param ) {
		return userAddressRepository.updateAddressByUserId( param.getId(), param.getModel() );
	}
}
