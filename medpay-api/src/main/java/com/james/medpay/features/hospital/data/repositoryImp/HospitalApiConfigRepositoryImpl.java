package com.james.medpay.features.hospital.data.repositoryImp;

import com.james.medpay.core.annotation.RepositoryImpl;
import com.james.medpay.core.params.Param;
import com.james.medpay.core.params.ParamValidator;
import com.james.medpay.features.hospital.domain.entity.HospitalApiConfiguration;
import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalEnvironmentEnum;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalApiConfigRepository;
import com.james.medpay.features.hospital.domain.repository.dataRepository._IHospitalApiConfigurationDataRepository;
import com.james.medpay.features.hospital.domain.repository.dataRepository._IHospitalDataRepository;

import java.util.Optional;

import static com.james.medpay.core.params.ParamValidator.*;

@RepositoryImpl
public class HospitalApiConfigRepositoryImpl implements IHospitalApiConfigRepository {

	private final _IHospitalDataRepository mainDataRepository;
	private final _IHospitalApiConfigurationDataRepository apiConfigurationDataRepository;

	public HospitalApiConfigRepositoryImpl(
			_IHospitalDataRepository mainRepository,
			_IHospitalApiConfigurationDataRepository apiConfigurationDataRepository

	) {
		this.mainDataRepository = mainRepository;
		this.apiConfigurationDataRepository = apiConfigurationDataRepository;
	}

	@Override
	public Optional<HospitalApiConfiguration> getHospitalApiConfiguration(
			Long hospitalId
	) {
		Optional<HospitalEntity> optional = this._getHospitalByIdFromHospitalDataRepo( hospitalId );
		return optional.map( HospitalEntity::getApiConfiguration );
	}

	@Override
	public Optional<HospitalApiConfiguration> updateHospitalApiConfiguration(
			Param<HospitalApiConfiguration> param
	) {
		ParamValidator._throwInvalidRequestExceptionIfParamHasAnyNullProps( param, HOSPITAL_ID, API_CONFIG );
		Optional<HospitalEntity> optional = _getHospitalByIdFromHospitalDataRepo( param.getId() );
		return _updateHospitalApiConfigurationIfHospitalExist( optional, param.getModel() );
	}

	private Optional<HospitalApiConfiguration> _updateHospitalApiConfigurationIfHospitalExist(
			Optional<HospitalEntity> optional, HospitalApiConfiguration configuration
	) {
		if ( optional.isPresent() ) {
			HospitalEntity hospital = optional.get();
			HospitalApiConfiguration saved = this.apiConfigurationDataRepository.save( configuration );
			hospital.setApiConfiguration( saved );
			this.mainDataRepository.save( hospital );
			return Optional.of( hospital.getApiConfiguration() );
		}
		return Optional.empty();
	}

	@Override
	public Optional<Boolean> updateHospitalApiActiveEnvironment(
			Param<HospitalEnvironmentEnum> param
	) {
		ParamValidator._throwInvalidRequestExceptionIfParamHasAnyNullProps( param, HOSPITAL_ID, ACTIVE_ENV );
		Optional<HospitalEntity> optional = this._getHospitalByIdFromHospitalDataRepo( param.getId() );
		return _updateHospitalApiActiveEnvIfPresent( optional, param.getModel() );
	}

	private Optional<Boolean> _updateHospitalApiActiveEnvIfPresent(
			Optional<HospitalEntity> optional,
			HospitalEnvironmentEnum model
	) {
		if ( optional.isPresent() ) {
			HospitalEntity hospital = optional.get();
			HospitalApiConfiguration apiConfiguration = hospital.getApiConfiguration();
			apiConfiguration.setEnvironmentEnum( model );
			hospital.setApiConfiguration( apiConfiguration );
			mainDataRepository.save( hospital );
			return Optional.of( true );
		}
		return Optional.of( false );
	}

	private Optional<HospitalEntity> _getHospitalByIdFromHospitalDataRepo( Long id ) {
		return this.mainDataRepository.findById( id );
	}

}
