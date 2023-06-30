package com.james.medpay.features.authentication.presenter;

import com.james.medpay.features.authentication.data.interactor.contract.IAuthRoleInteractor;
import com.james.medpay.features.authentication.data.request.PermissionRequest;
import com.james.medpay.features.authentication.data.request.RolePermissions;
import com.james.medpay.features.authentication.data.request.RoleRequest;
import com.james.medpay.features.authentication.domain.entity.enums.AuthSearchByEnum;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.james.medpay.core.constant.Routes.API_PREFIX;

@RestController
@RequiredArgsConstructor
@RequestMapping( value = API_PREFIX + "/auth-role" )
public class AuthRoleEntryPoint {
	private final IAuthRoleInteractor roleInteractor;

	@PostMapping( value = "/create" )
	ResponseEntity< RoleRequest > create(@Valid @RequestBody RoleRequest role) {
		return this.roleInteractor.create(role);
	}

	@GetMapping( value = "/all" )
	ResponseEntity< List< RoleRequest > > getAll(@RequestParam( value = "trim", required = false, defaultValue = "true" ) boolean trim) {
		return roleInteractor.getAll(trim);
	}

	@GetMapping( value = "/{value}" )
	ResponseEntity< ? > getByIdOrName(@RequestParam( value = "searchBy", required = false, defaultValue = "ID" ) AuthSearchByEnum searchBy, @PathVariable( value = "value" ) String value) {
		return roleInteractor.getByIdOrName(value, searchBy);
	}

	@GetMapping( value = "/{roleId}/usage" )
	ResponseEntity< Optional< Integer > > getUsageCount(@PathVariable( value = "roleId" ) Long roleId) {
		return roleInteractor.getUsageCount(roleId);
	}

	@GetMapping( value = "/{roleId}/permissions" )
	ResponseEntity< List< PermissionRequest > > getPermissions(@PathVariable( value = "roleId" ) Long roleId) {
		return roleInteractor.getPermissions(roleId);
	}

	@GetMapping( value = "/{roleId}/status" )
	ResponseEntity< Optional< Boolean > > getStatusId(@PathVariable( value = "roleId" ) Long roleId) {
		return roleInteractor.getStatusId(roleId);
	}

	@GetMapping( value = "/{roleId}/can-disable" )
	ResponseEntity< Optional< Boolean > > getCanDisableFlag(@PathVariable( value = "roleId" ) Long roleId) {
		return roleInteractor.getCanDisableFlag(roleId);
	}

	@PatchMapping( value = "/{roleId}/update-status" )
	ResponseEntity< Optional< Boolean > > updateStatusById(@PathVariable( value = "roleId" ) Long roleId, @RequestParam( value = "status" ) boolean status) {
		return roleInteractor.updateStatusById(roleId, status);
	}

	@PostMapping( value = "/{roleId}/update-permissions" )
	ResponseEntity< Optional< Boolean > > updatePermissions(@PathVariable( value = "roleId" ) Long roleId, @RequestBody RolePermissions rPerm) {
		return roleInteractor.updatePermissions(roleId, rPerm.getPermissions());
	}

	@PutMapping( value = "/update" )
	ResponseEntity< Optional< RoleRequest > > updateById(@Valid @RequestBody RoleRequest role) {
		return roleInteractor.updateById(role);
	}

}
