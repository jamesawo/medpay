package com.james.medpay.features.authentication.data.interactor.implementation;

import com.james.medpay.core.annotation.Interactor;
import com.james.medpay.core.params.BiParam;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.authentication.data.interactor.contract.IAuthUserInteractor;
import com.james.medpay.features.authentication.data.request.PasswordResetRequest;
import com.james.medpay.features.authentication.data.request.RoleRequest;
import com.james.medpay.features.authentication.domain.entity.Role;
import com.james.medpay.features.authentication.domain.usecase.authUserUsecase.AuthUserAssignRolesUsecase;
import com.james.medpay.features.authentication.domain.usecase.authUserUsecase.AuthUserGetRolesUsecase;
import com.james.medpay.features.authentication.domain.usecase.authUserUsecase.AuthUserResetPasswordUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@Interactor
@RequiredArgsConstructor
public class AuthUserInteractorImpl implements IAuthUserInteractor {

	private final AuthUserAssignRolesUsecase authUserAssignRolesUsecase;
	private final AuthUserGetRolesUsecase getRolesUsecase;
	private final AuthUserResetPasswordUsecase resetPasswordUsecase;

	@Override
	public ResponseEntity<Optional<Boolean>> assignRolesToUser(
			Long userId, List<RoleRequest> roleRequests
	) {
		List<Role> list = roleRequests.stream().map( RoleRequest::toEntity ).collect( Collectors.toList() );
		return ok().body( this.authUserAssignRolesUsecase.execute( new Param<>( userId, list ) ) );
	}

	@Override
	public ResponseEntity<List<RoleRequest>> getUserRoles( Long userId ) {
		Collection<Role> collection = this.getRolesUsecase.execute( userId );
		return ok().body( collection.stream().map( RoleRequest::toRequest ).collect( Collectors.toList() ) );
	}

	@Override
	public ResponseEntity<Optional<Boolean>> resetUserPassword( PasswordResetRequest request ) {
		Long userId = request.getUser().getId();
		BiParam<String, String> biParam = new BiParam<>(request.getPassword(), request.getConfirmPassword());
		Optional<Boolean> optional = resetPasswordUsecase.execute( new Param<>( userId, biParam ) );
		return ok().body( optional );
	}
}
