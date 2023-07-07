package com.james.medpay.features.hospital.data.repositoryImp;

import com.james.medpay.core.annotation.RepositoryImpl;
import com.james.medpay.core.exception.InvalidRequestException;
import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.features.hospital.domain.entity.HospitalRevenueHead;
import com.james.medpay.features.hospital.domain.entity.HospitalService;
import com.james.medpay.features.hospital.domain.entity.HospitalServiceGroup;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalBasicDetailRepository;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalRevenueHeadRepository;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalServiceRepository;
import com.james.medpay.features.hospital.domain.repository.dataRepository.IHospitalServiceDataRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Optional.of;

@RepositoryImpl
@RequiredArgsConstructor
public class HospitalServiceRepositoryImpl implements IHospitalServiceRepository {

	private final IHospitalServiceDataRepository serviceDataRepository;
	private final IHospitalRevenueHeadRepository revenueHeadRepository;
	private final IHospitalBasicDetailRepository basicDetailRepository;

	public static final String DUPLICATE = "Possible duplicate: a service with same title in revenue head already exist";

	private Long hospitalId;

	@Override
	public Optional<HospitalService> findOne(Long id) {
		return this.serviceDataRepository.findById(id);
	}

	@Override
	public Optional<HospitalService> createService( Long revHeadId, HospitalService service ) {
		Optional<HospitalRevenueHead> optional = this.revenueHeadRepository.getRevenueHeadById( revHeadId );
		return optional.map( revenueHead -> checkDuplicateBeforeCreate( revenueHead, service ) );
	}

	private HospitalService checkDuplicateBeforeCreate( HospitalRevenueHead revenueHead, HospitalService service ) {
		this._ifTitleAlreadyExistThenThrow( revenueHead, service.getTitle() );
		service.setRevenueHead( revenueHead );
		return this.serviceDataRepository.save( service );
	}

	private void _ifTitleAlreadyExistThenThrow( HospitalRevenueHead revenueHead, String title ) {
		Optional<HospitalService> optional = this.serviceDataRepository
				.findByRevenueHeadAndTitleIgnoreCase( revenueHead, title );
		if ( optional.isPresent() ) {
			throw new InvalidRequestException( DUPLICATE );
		}
	}

	@Override
	public Optional<Boolean> updateService( HospitalService service ) {
		Optional<HospitalService> optional = this.serviceDataRepository.findById( service.getId() );
		return optional.map( hospitalService -> {
			hospitalService.setTitle( service.getTitle() );
			hospitalService.setIsEnabled( service.getIsEnabled() );
			this.serviceDataRepository.save( hospitalService );
			return of( true );
		} ).orElse( of( false ) );
	}

	@Override
	public List<HospitalService> getAllServiceInRevenueHead( Long revHeadId ) {
		Optional<HospitalRevenueHead> optional = this.revenueHeadRepository.getRevenueHeadById( revHeadId );
		return optional.map( this.serviceDataRepository::findAllByRevenueHead ).orElse( new ArrayList<>() );
	}

	@Override
	public Optional<Boolean> updateStatus( Long serId, boolean status ) {
		Optional<HospitalService> optional = this.serviceDataRepository.findById( serId );
		return optional.map( service -> {
			int result = this.serviceDataRepository.updateStatusById( status, service.getId() );
			return of( result != 0 );
		} ).orElse( of( false ) );
	}

	@Override
	public List<HospitalService> searchWithTitleAndFilterByHospital( Long hospitalId, String title ) {
		Optional<HospitalEntity> optional = this.basicDetailRepository.getHospitalById( hospitalId );
		return optional.map( hospital -> {
			this.hospitalId = hospital.getId();
			List<HospitalService> list = this.serviceDataRepository.findAllByTitleIsContainingIgnoreCase( title );
			return list.stream().filter( isServiceBelongToHospitalPredicate ).collect( Collectors.toList() );
		} ).orElse( new ArrayList<>() );
	}

	private final Predicate<HospitalService> isServiceBelongToHospitalPredicate = hospitalService -> {
		HospitalRevenueHead revenueHead = hospitalService.getRevenueHead();
		HospitalServiceGroup serviceGroup = revenueHead.getServiceGroup();
		return serviceGroup.getHospital().getId().equals( hospitalId );
	};

}
