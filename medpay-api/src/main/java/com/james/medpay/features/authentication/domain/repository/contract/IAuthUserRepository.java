package com.james.medpay.features.authentication.domain.repository.contract;

import com.james.medpay.core.params.BiParam;
import com.james.medpay.features.authentication.domain.entity.Role;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IAuthUserRepository {
	Optional<Boolean> assignRolesToUser( Long userId, List<Role> list );

	Collection<Role> getUserRoles( Long userId );

	Optional<Boolean> resetUserPassword( Long userId, BiParam<String, String> passwordAndConfirmPassword);

}
