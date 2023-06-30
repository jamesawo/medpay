
package com.james.medpay.features.users.domain.repository.contract;

import com.james.medpay.features.authentication.domain.entity.Role;
import com.james.medpay.features.users.domain.entity.UserEntity;
import com.james.medpay.features.users.domain.entity.contract.IUserEntity;
import com.james.medpay.features.users.domain.entity.enums.UserTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IUserEntityRepository {

	IUserEntity createUser( UserEntity user );

	Optional<IUserEntity> getUserById( Long id );

	Optional<Boolean> updateUserIsEnabledStatusById( Long id, boolean status );

	Optional<Boolean> updateUserExpiryDateById( Long id, LocalDate date );

	Optional<Boolean> updateUserTypeById( Long id, UserTypeEnum userType );

	List<IUserEntity> getAllUsers();

	Page<IUserEntity> getUserPaginate();

	Optional<IUserEntity> getByEmail( String email );

	List<IUserEntity> getBySpecificationCriteria( Specification<UserEntity> specification);

	Optional<IUserEntity> updateAddressAndLimit(UserEntity user);

	Optional<Boolean> updateUserRoles( Long userId, Collection<Role> collection );

	Collection<Role> getUserRoles(Long userId);

	Optional<Boolean> resetPassword(UserEntity user, String password);
}
