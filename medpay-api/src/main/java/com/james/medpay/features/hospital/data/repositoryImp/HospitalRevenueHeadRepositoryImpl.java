package com.james.medpay.features.hospital.data.repositoryImp;

import com.james.medpay.core.annotation.RepositoryImpl;
import com.james.medpay.core.exception.InvalidRequestException;
import com.james.medpay.core.params.Param;
import com.james.medpay.core.params.ParamValidator;
import com.james.medpay.features.hospital.domain.entity.HospitalRevenueHead;
import com.james.medpay.features.hospital.domain.entity.HospitalServiceGroup;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalRevenueHeadRepository;
import com.james.medpay.features.hospital.domain.repository.dataRepository._IHospitalRevenueHeadDataRepository;
import com.james.medpay.features.hospital.domain.repository.dataRepository._IHospitalServiceGroupDataRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;


import static com.james.medpay.core.params.ParamValidator.GROUP_ID;
import static com.james.medpay.core.params.ParamValidator.REVENUE_HEAD;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@RepositoryImpl
@RequiredArgsConstructor
public class HospitalRevenueHeadRepositoryImpl implements IHospitalRevenueHeadRepository {
	public static final String DUPLICATE = "Possible Duplicate, %s already exist. in %s";
	private final _IHospitalServiceGroupDataRepository serviceGroupDataRepository;
	private final _IHospitalRevenueHeadDataRepository revenueHeadDataRepository;


	@Override
	public Optional<HospitalRevenueHead> createHospitalRevenueHeadInServiceGroup( Param<HospitalRevenueHead> params ) {
		ParamValidator._throwInvalidRequestExceptionIfParamHasAnyNullProps( params, GROUP_ID, REVENUE_HEAD );
		Optional<HospitalServiceGroup> optional = serviceGroupDataRepository.findById( params.getId() );
		return optional.map( serviceGroup -> _createRevenueHead( serviceGroup, params.getModel() ) );
	}

	private HospitalRevenueHead _createRevenueHead( HospitalServiceGroup serviceGroup, HospitalRevenueHead revenueHead ) {
		_checkIfDuplicateEntryBeforeSave( serviceGroup, revenueHead );
		revenueHead.setServiceGroup( serviceGroup );
		return this.revenueHeadDataRepository.save( revenueHead );
	}

	private void _checkIfDuplicateEntryBeforeSave( HospitalServiceGroup serviceGroup, HospitalRevenueHead revenueHead ) {
		String revenueHeadTitle = revenueHead.getTitle();
		String serviceGroupTitle = serviceGroup.getTitle();
		Optional<HospitalRevenueHead> optional = this.revenueHeadDataRepository
				.findByTitleAndServiceGroup( revenueHeadTitle, serviceGroup );
		if ( optional.isPresent() ) {
			throw new InvalidRequestException(
					String.format( DUPLICATE, revenueHeadTitle, serviceGroupTitle ) );
		}
	}

	@Override
	public List<HospitalRevenueHead> getAllRevenueHeadsInServiceGroup( Long serviceGroupId ) {
		Optional<HospitalServiceGroup> optional = this.serviceGroupDataRepository.findById( serviceGroupId );
		return optional.map( this.revenueHeadDataRepository::findAllByServiceGroup ).orElse( new ArrayList<>() );
	}

	@Override
	public Optional<HospitalRevenueHead> getRevenueHeadById( Long hospitalHeadId ) {
		return this.revenueHeadDataRepository.findById( hospitalHeadId );
	}

	@Override
	public Optional<HospitalRevenueHead> updateRevenueHead( HospitalRevenueHead newRevenueHeadWithChanges ) {
		_checkRevenueHeadHasEmptyProps(newRevenueHeadWithChanges);
		Optional<HospitalRevenueHead> optional = this.revenueHeadDataRepository.findById( newRevenueHeadWithChanges.getId() );
		return optional.map( existingRevenueHead -> {
			_setRevenueHead(existingRevenueHead, newRevenueHeadWithChanges);
			return this.revenueHeadDataRepository.save( existingRevenueHead );
		} );
	}

	private void _setRevenueHead(HospitalRevenueHead existing, HospitalRevenueHead toUpdate){
		existing.setTitle( toUpdate.getTitle() );
		existing.setCategory( toUpdate.getCategory() );
		existing.setIsEnabled( toUpdate.getIsEnabled() );
	}


	private void _checkRevenueHeadHasEmptyProps(HospitalRevenueHead revenueHead){
		if ( isEmpty( revenueHead ) || isEmpty( revenueHead.getId() ) ){
			throw new InvalidRequestException( "Invalid revenue head details" );
		}
	}

	@Override
	public Optional<Boolean> updateRevenueHeadStatus( Long revenueHead, boolean status ) {
		Optional<HospitalRevenueHead> optional = this.getRevenueHeadById( revenueHead );
		return optional.map( head -> _updateStatus( head, status ) ).orElse( Optional.of( false ) );
	}

	private Optional<Boolean> _updateStatus( HospitalRevenueHead head, boolean status ) {
		head.setIsEnabled( status );
		this.revenueHeadDataRepository.save( head );
		return Optional.of( true );
	}
}
