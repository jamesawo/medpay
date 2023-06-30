package com.james.medpay.features.authentication.data.interactor.contract;

import com.james.medpay.features.authentication.data.request.PermissionGroupRequest;
import com.james.medpay.features.authentication.data.request.PermissionRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface IAuthPermissionInteractor {
	ResponseEntity<Set<PermissionRequest>> getAllPermissions();

	ResponseEntity<List<PermissionGroupRequest> > getAllPermissionsGroupedByModule();
}
