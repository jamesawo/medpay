package com.james.medpay.features.authentication.data.interactor.implementation;

import com.james.medpay.core.annotation.Interactor;
import com.james.medpay.core.params.EmptyParam;
import com.james.medpay.features.authentication.data.interactor.contract.IAuthPermissionInteractor;
import com.james.medpay.features.authentication.data.request.PermissionGroupRequest;
import com.james.medpay.features.authentication.data.request.PermissionRequest;
import com.james.medpay.features.authentication.domain.entity.Permission;
import com.james.medpay.features.authentication.domain.entity.contract.IPermission;
import com.james.medpay.features.authentication.domain.usecase.permissionUsecase.AuthPermissionGetAllUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@Interactor
@RequiredArgsConstructor
public class AuthPermissionInteractorImpl implements IAuthPermissionInteractor {

	private final AuthPermissionGetAllUsecase permissionGetAllUsecase;

	@Override
	public ResponseEntity<Set<PermissionRequest>> getAllPermissions() {
		Set<IPermission> set = this.permissionGetAllUsecase.execute(new EmptyParam());
		Set<PermissionRequest> collect = set.stream()
				.map(permission -> PermissionRequest.toRequest((Permission) permission)).collect(
						Collectors.toSet());
		return ok(collect);
	}

	@Override
	public ResponseEntity<List<PermissionGroupRequest>> getAllPermissionsGroupedByModule() {
		Set<IPermission> set = this.permissionGetAllUsecase.execute(new EmptyParam());
		List<PermissionGroupRequest> collect = set.stream()
				.map(permission -> PermissionRequest.toRequest((Permission) permission))
				.collect(Collectors.groupingBy(PermissionRequest::getModule))
				.entrySet().stream().map(auth -> new PermissionGroupRequest(auth.getKey(), auth.getValue()))
				.collect(Collectors.toList());
		return ok().body(collect);
	}
}
