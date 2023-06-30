package com.james.medpay.features.authentication.data.request;

import lombok.Data;

import java.util.List;

@Data
public class RolePermissions {
	private Long roleId;
	private List<PermissionRequest> permissions;
}
