package com.james.medpay.features.authentication.data.interactor.contract;

import com.james.medpay.features.authentication.data.request.PermissionRequest;
import com.james.medpay.features.authentication.data.request.RoleRequest;
import com.james.medpay.features.authentication.domain.entity.enums.AuthSearchByEnum;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IAuthRoleInteractor {

	ResponseEntity<RoleRequest> create( RoleRequest role );

	ResponseEntity<List<RoleRequest>> getAll(boolean trim);

	ResponseEntity<Optional<RoleRequest>> getById( Long id );

	ResponseEntity<?> getByIdOrName( String value, AuthSearchByEnum roleByEnum );

	ResponseEntity<List<RoleRequest>> getByName( String name );

	ResponseEntity<Optional<Integer>> getUsageCount( Long id );

	ResponseEntity<List<PermissionRequest>> getPermissions( Long id );

	ResponseEntity<Optional<Boolean>> getStatusId( Long id );

	ResponseEntity<Optional<Boolean>> getCanDisableFlag( Long id );

	ResponseEntity<Optional<RoleRequest>> updateById( RoleRequest role );

	ResponseEntity<Optional<Boolean>> updateStatusById( Long id, boolean status );

	ResponseEntity<Optional<Boolean>> updatePermissions( Long id, List<PermissionRequest> permission );

}
