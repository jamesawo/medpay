package com.james.medpay.features.authentication.presenter;

import com.james.medpay.features.authentication.data.interactor.contract.IAuthPermissionInteractor;
import com.james.medpay.features.authentication.data.request.PermissionGroupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.james.medpay.core.constant.Routes.API_PREFIX;

@RestController
@RequestMapping( value = API_PREFIX + "/auth-permission" )
@RequiredArgsConstructor
public class AuthPermissionEntryPoint {
	private final IAuthPermissionInteractor interactor;

	@GetMapping( value = "/all" )
	ResponseEntity<List<PermissionGroupRequest> > getAllPermissionsGroupedByModule() {
		return interactor.getAllPermissionsGroupedByModule();
	}
}
