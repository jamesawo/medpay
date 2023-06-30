
package com.james.medpay.features.users.domain.usecase.usersBaseUsecase;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.users.domain.entity.UserEntity;
import com.james.medpay.features.users.domain.entity.contract.IUserEntity;
import com.james.medpay.features.users.domain.repository.contract.IUserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class UserGetBySearchRequestUsecase implements UsecaseContract<List<IUserEntity>, Specification<UserEntity>> {
	private final IUserEntityRepository repository;

	@Override
	public List<IUserEntity> execute( Specification<UserEntity> searchSpecification ) {
		return repository.getBySpecificationCriteria( searchSpecification );
	}
}
