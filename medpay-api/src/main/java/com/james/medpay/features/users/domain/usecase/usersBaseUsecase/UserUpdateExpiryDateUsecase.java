
package com.james.medpay.features.users.domain.usecase.usersBaseUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.users.domain.repository.contract.IUserEntityRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class UserUpdateExpiryDateUsecase implements UsecaseContract<Optional<Boolean>, Param<LocalDate>> {

	private final IUserEntityRepository repository;

	@Override
	public Optional<Boolean> execute( Param<LocalDate> param ) {
		return repository.updateUserExpiryDateById( param.getId(), param.getModel() );
	}
}
