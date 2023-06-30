
package com.james.medpay.features.users.domain.usecase.addressUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.users.domain.repository.contract.IUserAddressRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class UserSetEmergencyAddressVerifiedStatusUsecase implements UsecaseContract<Optional<Boolean>, Param<Boolean>> {

	private final IUserAddressRepository repository;


	@Override
	public Optional<Boolean> execute( Param<Boolean> param ) {
		return repository.setEmergencyAddressIsVerifiedStatusByUserId( param.getId(), param.getModel() );
	}
}
