
package com.james.medpay.features.users.data.interactor.implementation;

import com.james.medpay.core.annotation.Interactor;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.users.data.interactor.contract.IUserConfigurationInteractor;
import com.james.medpay.features.users.data.request.UserConfigurationRequest;
import com.james.medpay.features.users.domain.entity.UserConfigurationEntity;
import com.james.medpay.features.users.domain.entity.contract.IUserConfigurationEntity;
import com.james.medpay.features.users.domain.entity.enums.MinOrMaxTypeEnum;
import com.james.medpay.features.users.domain.entity.enums.UserLimitTypeEnum;
import com.james.medpay.features.users.domain.usecase.configurationUsecase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.springframework.http.ResponseEntity.of;
import static org.springframework.http.ResponseEntity.ok;

@Interactor
@RequiredArgsConstructor
public class UserConfigurationInteractor implements IUserConfigurationInteractor {

	private final UserGetConfigurationUsecase getConfigurationUsecase;
	private final UserUpdateConfigurationStatusUsecase updateConfigurationStatusUsecase;
	private final UserUpdateConfigurationUsecase updateConfigurationUsecase;
	private final UserUpdateLimitTypeUsecase updateLimitTypeUsecase;
	private final UserUpdateMaxLimitUsecase updateMaxLimitUsecase;
	private final UserUpdateMinLimitUsecase updateMinLimitUsecase;

	@Override
	public ResponseEntity<Optional<UserConfigurationRequest>> getConfigurationByUserId(
			Long userId
	) {
		UserConfigurationRequest request = new UserConfigurationRequest();
		Optional<IUserConfigurationEntity> optional = this.getConfigurationUsecase.execute( userId );
		return ok().body( optional.map( config -> {
			UserConfigurationEntity entity = ( UserConfigurationEntity ) config;
			return request.toRequest( entity );
		} ) );
	}

	@Override
	public ResponseEntity<Optional<UserConfigurationRequest>> updateConfigurationByUserId(
			Long userId, UserConfigurationRequest configuration
	) {
		UserConfigurationRequest request = new UserConfigurationRequest();
		UserConfigurationEntity entity = request.toEntity( configuration );
		Optional<IUserConfigurationEntity> optional =
				this.updateConfigurationUsecase.execute( new Param<>( userId, entity ) );
		return ok().body(
				optional.map( config -> {
					UserConfigurationEntity userConfigurationEntity = ( UserConfigurationEntity ) config;
					return request.toRequest( userConfigurationEntity );
				} )
		);
	}

	@Override
	public ResponseEntity<Optional<Boolean>> updateConfigurationStatusByUserId( Long userId, boolean status ) {
		Optional<Boolean> optional = this.updateConfigurationStatusUsecase.execute( new Param<>( userId, status ) );
		return ok().body( optional );
	}

	@Override
	public ResponseEntity<Optional<Boolean>> updateLimitTypeByUserId(
			Long userId, UserLimitTypeEnum type
	) {
		Optional<Boolean> optional = this.updateLimitTypeUsecase.execute( new Param<>( userId, type ) );
		return ok().body( optional );
	}

	@Override
	public ResponseEntity<Optional<Boolean>> updateMinOrMaxLimitByUserId(
			Long userId, int value, MinOrMaxTypeEnum type
	) {
		switch ( type ) {
			case MAX:
				return this.updateMaxLimitByUserId( userId, value );
			case MIN:
				return this.updateMinLimitByUserId( userId, value );
			default:
				return of( empty() );
		}
	}


	@Override
	public ResponseEntity<Optional<Boolean>> updateMaxLimitByUserId( Long userId, int maxLimit ) {
		Optional<Boolean> optional = this.updateMaxLimitUsecase.execute( new Param<>( userId, maxLimit ) );
		return ok().body( optional );
	}

	@Override
	public ResponseEntity<Optional<Boolean>> updateMinLimitByUserId( Long userId, int minLimit ) {
		Optional<Boolean> optional = this.updateMinLimitUsecase.execute( new Param<>( userId, minLimit ) );
		return ok().body( optional );
	}
}
