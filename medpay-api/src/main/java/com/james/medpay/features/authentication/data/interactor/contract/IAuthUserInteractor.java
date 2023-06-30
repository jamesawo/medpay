package com.james.medpay.features.authentication.data.interactor.contract;

import com.james.medpay.features.authentication.data.request.PasswordResetRequest;
import com.james.medpay.features.authentication.data.request.RoleRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IAuthUserInteractor {

	ResponseEntity<Optional<Boolean>> assignRolesToUser(Long userId, List<RoleRequest> roleRequests);

	ResponseEntity<List<RoleRequest>> getUserRoles(Long userId);

	ResponseEntity<Optional<Boolean>> resetUserPassword(PasswordResetRequest request);
}
