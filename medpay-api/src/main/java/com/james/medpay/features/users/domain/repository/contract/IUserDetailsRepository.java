package com.james.medpay.features.users.domain.repository.contract;


import com.james.medpay.features.authentication.domain.entity.Permission;
import com.james.medpay.features.authentication.domain.entity.Role;
import com.james.medpay.features.users.domain.entity.UserEntity;

import java.util.Collection;
import java.util.List;

public interface IUserDetailsRepository {

	UserEntity findByUsername( String username );

	List<String> findUserPermissionsNameOnly( UserEntity user );

	Collection<Permission> findUserPermissions( UserEntity user );

	Collection<Permission> findUserPermissionsFromRoles( Collection<Role> roles );
}
