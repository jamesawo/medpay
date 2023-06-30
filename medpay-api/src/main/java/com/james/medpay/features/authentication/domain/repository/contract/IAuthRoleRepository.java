package com.james.medpay.features.authentication.domain.repository.contract;

import com.james.medpay.features.authentication.domain.entity.contract.IPermission;
import com.james.medpay.features.authentication.domain.entity.contract.IRole;

import java.util.List;
import java.util.Optional;

public interface IAuthRoleRepository {

	IRole create( IRole role );

	List<IRole> getAll();

	Optional<IRole> getById( Long id );

	List<IRole> getByName( String name );

	Optional<Integer> getUsageCount( Long id );

	List<IPermission> getPermissions( Long id );

	Optional<Boolean> getStatusId( Long id );

	Optional<Boolean> getCanDisableFlag( Long id );

	Optional<IRole> updateNameAndStatus( Long id, IRole role );

	Optional<Boolean> updateStatusById( Long id, boolean status );

	Optional<Boolean> updatePermissions( Long id, List<IPermission> permission );


}
