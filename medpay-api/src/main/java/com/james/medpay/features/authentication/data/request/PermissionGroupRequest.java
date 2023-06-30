package com.james.medpay.features.authentication.data.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.james.medpay.features.authentication.domain.entity.enums.AuthModuleEnum;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude( JsonInclude.Include.NON_NULL )
public class PermissionGroupRequest {
	private AuthModuleEnum module;
	private List<PermissionRequest> permissions;

	public PermissionGroupRequest(
			AuthModuleEnum module, List<PermissionRequest> permissions
	) {
		this.module = module;
		this.permissions = permissions;
	}
}
