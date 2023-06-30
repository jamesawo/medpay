package com.james.medpay.features.hospital.data.repositoryImp;

import com.james.medpay.core.annotation.RepositoryImpl;
import com.james.medpay.core.exception.InvalidRequestException;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.hospital.domain.entity.*;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalBasicDetailRepository;
import com.james.medpay.features.hospital.domain.repository.dataRepository.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


import static com.james.medpay.core.params.ParamValidator.*;
import static com.james.medpay.core.params.ParamValidator.HOSPITAL_DETAIL;
import static com.james.medpay.core.params.ParamValidator.HOSPITAL_ID;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@RepositoryImpl
public class HospitalBasicDetailRepositoryImpl implements IHospitalBasicDetailRepository {

	private static final String INVALID_HOSPITAL = "Invalid Hospital Provided";
	private static final String INVALID_SERVICE_GROUP = "Cannot Update Hospital Service Group with invalid entity";
	private static final String NO_DETAILS = "Error, Provide Hospital Basic Details";
	private static final String SERVICE_GROUP_SUFFIX = " Service Group";
	private static final String DATA_VIOLATION = "DataIntegrity Violation: Possible Duplicate Found ";

	private final _IHospitalDataRepository dataRepository;
	private final _IHospitalDetailDataRepository detailDataRepository;
	private final _IHospitalServiceGroupDataRepository serviceGroupDataRepository;
	private final _IHospitalApiConfigurationDataRepository apiConfigurationDataRepository;
	private final _IHospitalSettlementDataRepository settlementDataRepository;

	public HospitalBasicDetailRepositoryImpl(
			_IHospitalDataRepository dataRepository,
			_IHospitalDetailDataRepository detailDataRepository,
			_IHospitalServiceGroupDataRepository serviceGroupDataRepository,
			_IHospitalApiConfigurationDataRepository apiConfigurationDataRepository,
			_IHospitalSettlementDataRepository settlementDataRepository
	) {
		this.dataRepository = dataRepository;
		this.detailDataRepository = detailDataRepository;
		this.serviceGroupDataRepository = serviceGroupDataRepository;
		this.apiConfigurationDataRepository = apiConfigurationDataRepository;
		this.settlementDataRepository = settlementDataRepository;
	}

	@Override
	public List<HospitalEntity> getAllHospitals() {
		return dataRepository.findAll();
	}

	@Override
	public Page<HospitalEntity> getHospitalsInPages() {
		return null;
	}

	@Override
	@Transactional
	public HospitalEntity createHospital( HospitalEntity request ) {
		_throwInvalidRequestExceptionIfHospitalRequestIsNull( request );
		try {
			this._saveHospitalRequestPartialModels( request );
			return dataRepository.save( request );
		} catch ( DataIntegrityViolationException e ) {
			String message = e.getMessage() != null ? DATA_VIOLATION + e.getMessage() : DATA_VIOLATION;
			throw new DataIntegrityViolationException( message, e );
		} catch ( Exception e ) {
			throw new RuntimeException( e.getMessage(), e );
		}
	}

	private void _saveHospitalRequestPartialModels( HospitalEntity hospital ) {
		_saveAndSetDetail( hospital );
		_saveAndSetApiConfig( hospital );
		//_saveAndSetServiceGroup( hospital );
		hospital.setServiceGroup( null );
		_saveSettlement( hospital );
	}

	private void _saveAndSetDetail( HospitalEntity hospital ) {
		HospitalDetail detail = this.detailDataRepository.save( hospital.getDetail() );
		hospital.setDetail( detail );
	}

	private void _saveAndSetApiConfig( HospitalEntity hospital ) {
		if ( hospital.getApiConfiguration() == null ) {
			hospital.setApiConfiguration( new HospitalApiConfiguration() );
		}
		HospitalApiConfiguration saved = apiConfigurationDataRepository.save( hospital.getApiConfiguration() );
		hospital.setApiConfiguration( saved );
	}

	private void _saveAndSetServiceGroup( HospitalEntity hospital ) {
		if ( hospital.getServiceGroup() == null ) {
			HospitalServiceGroup serviceGroup = HospitalServiceGroup.builder().title(
					hospital.getDetail().getName() + SERVICE_GROUP_SUFFIX ).build();

			hospital.setServiceGroup( serviceGroup );
		}
		HospitalServiceGroup saved = serviceGroupDataRepository.save( hospital.getServiceGroup() );
		hospital.setServiceGroup( saved );
	}

	private void _saveSettlement( HospitalEntity hospital ) {
		if ( hospital.getSettlementConfiguration() == null ) {
			HospitalSettlementConfiguration settlementConfiguration = HospitalSettlementConfiguration.builder().build();
			hospital.setSettlementConfiguration( settlementConfiguration );
		}

		HospitalSettlementConfiguration saved = settlementDataRepository.save(
				hospital.getSettlementConfiguration() );
		hospital.setSettlementConfiguration( saved );
	}

	private void _throwInvalidRequestExceptionIfHospitalRequestIsNull( HospitalEntity hospital ) {
		if ( isEmpty( hospital.getDetail() ) ) {
			throw new InvalidRequestException( NO_DETAILS );
		}
	}

	@Override
	public Optional<HospitalEntity> getHospitalById( Long id ) {
		return this.dataRepository.findById( id );
	}

	@Override
	public Optional<HospitalEntity> getHospitalByIdWithDetailOnly( Long id ) {
		Optional<HospitalEntity> optional = this.dataRepository.findById( id );
		return optional.map( HospitalEntity::trim );
	}

	@Override
	public Optional<HospitalDetail> updateHospitalDetails( Param<HospitalDetail> params ) {
		_throwInvalidRequestExceptionIfParamHasAnyNullProps( params, HOSPITAL_ID, HOSPITAL_DETAIL );
		Optional<HospitalEntity> optional = this.dataRepository.findById( params.getId() );
		return _updateHospitalDetailsIfHospitalIsPresent( optional, params.getModel() );
	}

	@Override
	public List<HospitalEntity> getHospitalsWithSearchRequest(
			Specification<HospitalEntity> spec
	) {
		return this.dataRepository.findAll( spec );
	}

	@Override
	public Optional<HospitalEntity> updatePartialDetails( Param<HospitalEntity> param ) {
		Optional<HospitalEntity> optional = this.dataRepository.findById( param.getId() );
		return optional.map( hospital -> this._updateHospitalPartProperties( hospital, param.getModel() ) );
	}

	@Override
	public HospitalEntity updateServiceGroup(
			HospitalEntity hospital, HospitalServiceGroup serviceGroup
	) {
		_checkServiceGroupIsNotNullOrThrow(hospital, serviceGroup );
		hospital.setServiceGroup( serviceGroup );
		return this.dataRepository.save( hospital );
	}

	private void _checkServiceGroupIsNotNullOrThrow( HospitalEntity hospital, HospitalServiceGroup serviceGroup ) {
		if ( isEmpty( hospital ) || isEmpty( hospital.getId() ) ) {
			throw new InvalidRequestException( INVALID_HOSPITAL );
		}
		if ( isEmpty( serviceGroup ) || isEmpty( serviceGroup.getId() ) ) {
			throw new InvalidRequestException( INVALID_SERVICE_GROUP );
		}
	}

	private HospitalEntity _updateHospitalPartProperties( HospitalEntity hospital, HospitalEntity toUpdate ) {
		hospital.setHasHospitalSoftware( toUpdate.getHasHospitalSoftware() );
		hospital.setCollectionModelEnum( toUpdate.getCollectionModelEnum() );
		hospital.setExpiryDate( toUpdate.getExpiryDate() );
		hospital.setIsEnabled( toUpdate.getIsEnabled() );
		return this.dataRepository.save( hospital );
	}

	private Optional<HospitalDetail> _updateHospitalDetailsIfHospitalIsPresent(
			Optional<HospitalEntity> optional,
			HospitalDetail model
	) {
		if ( optional.isPresent() ) {
			HospitalDetail detail = optional.get().getDetail();
			HospitalDetail saved = _createNewHospitalBeforeUpdateOrUpdateOnly( detail, model );
			return of( saved );
		}
		return empty();
	}

	private HospitalDetail _createNewHospitalBeforeUpdateOrUpdateOnly(
			HospitalDetail oldDetail, HospitalDetail newDetail
	) {
		try {
			oldDetail = oldDetail == null ? HospitalDetail.builder().build() : oldDetail;
			oldDetail.updateProperties( newDetail );
			return this.detailDataRepository.save( oldDetail );
		} catch ( Exception e ) {
			throw new InvalidRequestException( e.getMessage() );
		}
	}

	private HospitalEntity _findHospitalById( Long id ) {
		return this.dataRepository.findById( id )
		                          .orElseThrow( () -> new ResponseStatusException(
				                          HttpStatus.NOT_FOUND, "Ops! Hospital not found" )
		                          );
	}
}

