package com.james.medpay.features.authentication.data.repositoryImpl;

import com.james.medpay.core.annotation.RepositoryImpl;
import com.james.medpay.core.configuration.security.JwtProvider;
import com.james.medpay.core.exception.ApplicationException;
import com.james.medpay.core.exception.InvalidRequestException;
import com.james.medpay.features.authentication.domain.entity.partial.AuthLogin;
import com.james.medpay.features.authentication.domain.repository.contract.IAuthLoginRepository;
import com.james.medpay.features.hospital.data.request.HospitalRequest;
import com.james.medpay.features.users.domain.entity.contract.IUserEntity;
import com.james.medpay.features.users.domain.usecase.usersBaseUsecase.UserGetByEmailUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@RepositoryImpl
@RequiredArgsConstructor
public class AuthLoginRepositoryImpl implements IAuthLoginRepository {
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	private final PasswordEncoder passwordEncoder;
	private final UserGetByEmailUsecase getByEmailUsecase;

	private static final String INVALID_USERNAME = "Invalid Username/Password";
	private static final String ACCOUNT_EXPIRED = "Error! Account Is Expired";
	private static final String ACCOUNT_DISABLED = "Error! Account Is Currently Disabled";
	private static final String INVALID_LOGIN = "Invalid Login Credentials";

	@Override
	public AuthLogin login( String username, String password ) {
		Optional<IUserEntity> user = getByEmailUsecase.execute( username );
		return _validateFirstThenAuthenticateUser( user, password );
	}

	private AuthLogin _validateFirstThenAuthenticateUser( Optional<IUserEntity> user, String password ) {
		if ( user.isEmpty() ) {
			throw new InvalidRequestException( INVALID_USERNAME );
		}

		IUserEntity iUserEntity = user.get();
		this._validateUserAccount( iUserEntity, password );
		return _authenticateUser( iUserEntity, password );
	}

	private void _validateUserAccount( IUserEntity iUser, String password ) {
		this._throwsInvalidIfPasswordNotMatch( password, iUser );
		this._throwsInvalidIfAccountIsExpired( iUser );
		this._throwsInvalidIfAccountIsDisabled( iUser );
	}

	private void _throwsInvalidIfPasswordNotMatch( String password, IUserEntity iUser ) {
		if ( !this.passwordEncoder.matches( password, iUser.getPassword() ) ) {
			throw new InvalidRequestException( INVALID_LOGIN );
		}
	}

	private void _throwsInvalidIfAccountIsExpired( IUserEntity iUser ) {
		if ( iUser.isAccountExpired() ) {
			throw new InvalidRequestException( ACCOUNT_EXPIRED );
		}
	}

	private void _throwsInvalidIfAccountIsDisabled( IUserEntity iUser ) {
		if ( !iUser.getIsEnabled() ) {
			throw new InvalidRequestException( ACCOUNT_DISABLED );
		}
	}

	private AuthLogin _authenticateUser( IUserEntity user, String password ) {
		try {
			Authentication authentication = this._authenticate( user.getEmail(), password );
			this._setSecurityContextHolder( authentication );
			String token = this._jwtProviderGenerateToken( authentication );
			return _getAuthLoginResponse( user, token );
		} catch ( Exception e ) {
			throw new ApplicationException( e.getMessage() );
		}
	}

	// here: fix authentication manager
	private Authentication _authenticate( String username, String password ) {
		return this.authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( username, password ) );
	}

	private void _setSecurityContextHolder( Authentication authentication ) {
		SecurityContextHolder.getContext().setAuthentication( authentication );
	}

	private String _jwtProviderGenerateToken( Authentication authentication ) {
		return jwtProvider.generateToken( authentication );
	}

	private AuthLogin _getAuthLoginResponse( IUserEntity user, String jwtToken ) {
		AuthLogin login = new AuthLogin();
		login.setId( user.getId() );
		login.setUuid( user.getUuid() );
		login.setUsername( user.getEmail() );
		login.setToken( jwtToken );
		login.setType( user.getUserTypeEnum() );
		login.setFullName( user.getBasicDetails().getFullName() );
		login.setEmail( user.getEmail() );
		login.setMenu( _getUserMenu( user ) );
		login.setPhone( user.getBasicDetails().getPhoneNumber() );
		login.setEnabled( user.getIsEnabled() );
		login.setNickName( user.getNickName() );
		if( isNotEmpty(user.getHospital() ) && isNotEmpty( user.getHospital().getId() ) ) {
			user.getHospital().trim();
			login.setHospital( HospitalRequest.from(user.getHospital()) );
		}
		return login;
	}

	private String _getUserMenu( IUserEntity user ) {
		return "";
	}

}
