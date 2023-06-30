
package com.james.medpay.features.users.domain.usecase.basicDetailsUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.users.domain.entity.contract.IUserBasicDetailsEntity;
import com.james.medpay.features.users.domain.repository.contract.IUserBasicDetailRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class UserGetBasicDetailsUsecase implements UsecaseContract<Optional<IUserBasicDetailsEntity>, Long> {

	private final IUserBasicDetailRepository basicDetailRepository;

	@Override
	public Optional<IUserBasicDetailsEntity> execute(
			Long param
	) {
		return basicDetailRepository.getUserBasicDetailsByUserId( param );
	}
}
