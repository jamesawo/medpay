package com.james.medpay.features.hospital.data.interactor.implementation;

import com.james.medpay.core.annotation.Interactor;
import com.james.medpay.core.contract.DataMapper;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.hospital.data.interactor.contract.HospitalBaseInteractor;
import com.james.medpay.features.hospital.data.request.HospitalApiConfigurationRequest;
import com.james.medpay.features.hospital.domain.entity.HospitalApiConfiguration;
import com.james.medpay.features.hospital.domain.entity.contract.IHospitalApiConfiguration;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalEnvironmentEnum;
import com.james.medpay.features.hospital.domain.usecase.apiConfiguration.HospitalConfigureApiUsecase;
import com.james.medpay.features.hospital.domain.usecase.apiConfiguration.HospitalGetApiConfigurationUsecase;
import com.james.medpay.features.hospital.domain.usecase.apiConfiguration.HospitalUpdateActiveEnvironmentUsecase;
import com.james.medpay.features.hospital.domain.usecase.apiConfiguration.HospitalUpdateApiConfigurationUsecase;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@Interactor
public class HospitalApiConfigurationInteractor extends HospitalBaseInteractor implements DataMapper<HospitalApiConfigurationRequest, IHospitalApiConfiguration> {

	private final UsecaseContract<Optional<HospitalApiConfiguration>, Param<HospitalApiConfiguration>> setApiConfigurationUsecase;
	private final UsecaseContract<Optional<HospitalApiConfiguration>, Long> getApiConfigurationUsecase;
	private final UsecaseContract<Optional<Boolean>, Param<HospitalEnvironmentEnum>> updateActiveEnvironmentUsecase;
	private final UsecaseContract<
			Optional<HospitalApiConfiguration>, Param<HospitalApiConfiguration>> updateApiConfigurationUsecase;

	public HospitalApiConfigurationInteractor(
			HospitalConfigureApiUsecase apiUsecase,
			HospitalGetApiConfigurationUsecase configurationUsecase,
			HospitalUpdateActiveEnvironmentUsecase updateActiveEnvironmentUsecase,
			HospitalUpdateApiConfigurationUsecase updateApiConfigurationUsecase
	) {
		this.setApiConfigurationUsecase = apiUsecase;
		this.getApiConfigurationUsecase = configurationUsecase;
		this.updateActiveEnvironmentUsecase = updateActiveEnvironmentUsecase;
		this.updateApiConfigurationUsecase = updateApiConfigurationUsecase;
	}


	@Override
	public ResponseEntity<Optional<HospitalApiConfigurationRequest>> createApiConfigurationUsecase(
			HospitalApiConfigurationRequest request, Long hospitalId
	) {
		Optional<HospitalApiConfiguration> configuration = setApiConfigurationUsecase.execute(
				new Param<>( hospitalId, toRequest( request ) ) );
		Optional<HospitalApiConfigurationRequest> configurationRequest = configuration.map( this::toEntity );
		return ResponseEntity.ok().body( configurationRequest );
	}

	@Override
	public ResponseEntity<Optional<HospitalApiConfigurationRequest>> getApiConfigurationUsecase(
			Long hospitalId
	) {
		Optional<HospitalApiConfiguration> optional = getApiConfigurationUsecase.execute( hospitalId );
		Optional<HospitalApiConfigurationRequest> hospitalApiConfigurationRequest = optional.map( this::toEntity );
		return ResponseEntity.ok().body( hospitalApiConfigurationRequest );
	}

	@Override
	public ResponseEntity<Optional<Boolean>> updateActiveEnvironmentUsecase(
			Long hospitalId, HospitalEnvironmentEnum environmentEnum
	) {
		Param<HospitalEnvironmentEnum> params = new Param<>( hospitalId, environmentEnum );
		Optional<Boolean> result = updateActiveEnvironmentUsecase.execute( params );
		return ResponseEntity.ok().body( result );

	}

	@Override
	public ResponseEntity<Optional<HospitalApiConfigurationRequest>> updateApiConfigurationUsecase(
			Long hospitalId, HospitalApiConfigurationRequest request
	) {

		Optional<HospitalApiConfiguration> optional = this.updateApiConfigurationUsecase.execute(
				new Param<>( hospitalId, toRequest( request ) ) );

		Optional<HospitalApiConfigurationRequest> apiConfigurationRequest = optional.map( this::toEntity );
		return ResponseEntity.ok().body( apiConfigurationRequest );

	}

	@Override
	public HospitalApiConfigurationRequest toEntity( IHospitalApiConfiguration configuration ) {
		return HospitalApiConfigurationRequest.toRequest( configuration );
	}


	@Override
	public HospitalApiConfiguration toRequest( HospitalApiConfigurationRequest request ) {
		return HospitalApiConfigurationRequest.toEntity( request );
	}

}
