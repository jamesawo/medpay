package com.james.medpay.features.authentication.data.interactor.implementation;

import com.james.medpay.core.annotation.Interactor;
import com.james.medpay.core.contract.DataMapper;
import com.james.medpay.core.params.EmptyParam;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.authentication.data.interactor.contract.IAuthRoleInteractor;
import com.james.medpay.features.authentication.data.request.PermissionRequest;
import com.james.medpay.features.authentication.data.request.RoleRequest;
import com.james.medpay.features.authentication.domain.entity.Permission;
import com.james.medpay.features.authentication.domain.entity.Role;
import com.james.medpay.features.authentication.domain.entity.contract.IPermission;
import com.james.medpay.features.authentication.domain.entity.contract.IRole;
import com.james.medpay.features.authentication.domain.entity.enums.AuthSearchByEnum;
import com.james.medpay.features.authentication.domain.usecase.roleUsecase.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;


import static java.util.Optional.empty;
import static org.springframework.http.ResponseEntity.ok;

@Interactor
@RequiredArgsConstructor
public class AuthRoleInteractorImpl implements IAuthRoleInteractor, DataMapper< IRole, RoleRequest > {
	private final AuthCreateRoleUsecase createRoleUsecase;
	private final AuthGetAllRolesUsecase getAllRolesUsecase;
	private final AuthGetRoleByIdUsecase getRoleByIdUsecase;
	private final AuthGetRoleByNameUsecase getRoleByNameUsecase;
	private final AuthGetRoleCanDisableFlagByIdUsecase getRoleCanDisableFlagByIdUsecase;
	private final AuthGetRolePermissionsByIdUsecase getRolePermissionsByIdUsecase;
	private final AuthGetRoleStatusByIdUsecase getRoleStatusByIdUsecase;
	private final AuthGetRoleUsageCountByIdUsecase getRoleUsageCountByIdUsecase;
	private final AuthUpdateRoleByIdUsecase updateRoleByIdUsecase;
	private final AuthUpdateRolePermissionsByIdUsecase updateRolePermissionsByIdUsecase;
	private final AuthUpdateRoleStatusByIdUsecase updateRoleStatusByIdUsecase;

	@Override
	public ResponseEntity< RoleRequest > create(RoleRequest role) {
		IRole result = this.createRoleUsecase.execute(this.toEntity(role));
		return ok().body(this.toRequest(result));
	}

	@Override
	public ResponseEntity< List< RoleRequest > > getAll(boolean trim) {
		List< IRole > list = this.getAllRolesUsecase.execute(new EmptyParam());
		List< RoleRequest > collect = list.stream().filter(IRole::isNotSuperRole).map(role -> {
			if( trim ) role.trim();
			return toRequest(role);
		}).collect(Collectors.toList());
		return ok().body(collect);
	}

	@Override
	public ResponseEntity< Optional< RoleRequest > > getById(Long id) {
		Optional< IRole > optional = this.getRoleByIdUsecase.execute(id);
		return ok().body(optional.map(this::toRequest));
	}

	@Override
	public ResponseEntity< ? > getByIdOrName(String value, AuthSearchByEnum roleByEnum) {
		switch( roleByEnum ) {
			case ID:
				return this.getById(Long.valueOf(value));
			case NAME:
				return this.getByName(value);
			default:
				return ok().body(empty());
		}
	}

	@Override
	public ResponseEntity< List< RoleRequest > > getByName(String name) {
		List< IRole > list = this.getRoleByNameUsecase.execute(name);
		return ok().body(list.stream().map(this::toRequest).collect(Collectors.toList()));
	}

	@Override
	public ResponseEntity< Optional< Integer > > getUsageCount(Long id) {
		Optional< Integer > optional = this.getRoleUsageCountByIdUsecase.execute(id);
		return ok().body(optional);
	}

	@Override
	public ResponseEntity< List< PermissionRequest > > getPermissions(Long id) {
		List< IPermission > list = this.getRolePermissionsByIdUsecase.execute(id);
		List< PermissionRequest > collect = list.stream().map(permission -> PermissionRequest.toRequest(( Permission ) permission)).collect(Collectors.toList());
		return ok().body(collect);
	}

	@Override
	public ResponseEntity< Optional< Boolean > > getStatusId(Long id) {
		Optional< Boolean > optional = this.getRoleStatusByIdUsecase.execute(id);
		return ok().body(optional);
	}

	@Override
	public ResponseEntity< Optional< Boolean > > getCanDisableFlag(Long id) {
		Optional< Boolean > optional = this.getRoleCanDisableFlagByIdUsecase.execute(id);
		return ok().body(optional);
	}

	@Override
	public ResponseEntity< Optional< RoleRequest > > updateById(RoleRequest role) {
		Optional< IRole > optional = this.updateRoleByIdUsecase.execute(new Param<>(role.getId(), toEntity(role)));
		return ok().body(optional.map(this::toRequest));
	}

	@Override
	public ResponseEntity< Optional< Boolean > > updateStatusById(Long id, boolean status) {
		Optional< Boolean > optional = this.updateRoleStatusByIdUsecase.execute(new Param<>(id, status));
		return ok().body(optional);
	}

	@Override
	public ResponseEntity< Optional< Boolean > > updatePermissions(Long id, List< PermissionRequest > permission) {
		List< IPermission > collect = permission.stream().map(PermissionRequest::toEntity).collect(Collectors.toList());
		Optional< Boolean > optional = this.updateRolePermissionsByIdUsecase.execute(new Param<>(id, collect));
		return ok().body(optional);
	}

	@Override
	public IRole toEntity(RoleRequest request) {
		return RoleRequest.toEntity(request);
	}

	@Override
	public RoleRequest toRequest(IRole role) {
		return RoleRequest.toRequest(( Role ) role);
	}
}
