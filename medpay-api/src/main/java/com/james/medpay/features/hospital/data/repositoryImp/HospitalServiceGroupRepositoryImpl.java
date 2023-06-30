package com.james.medpay.features.hospital.data.repositoryImp;

import com.james.medpay.core.annotation.RepositoryImpl;
import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.features.hospital.domain.entity.HospitalServiceGroup;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalBasicDetailRepository;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalServiceGroupRepository;
import com.james.medpay.features.hospital.domain.repository.dataRepository._IHospitalServiceGroupDataRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;

@RepositoryImpl
@RequiredArgsConstructor
public class HospitalServiceGroupRepositoryImpl implements IHospitalServiceGroupRepository {

	private final _IHospitalServiceGroupDataRepository serviceGroupDataRepository;
	private final IHospitalBasicDetailRepository basicDetailRepository;

	@Override
	public Optional<HospitalServiceGroup> updateServiceGroup(
			Long hospitalId, HospitalServiceGroup serviceGroup
	) {
		Optional<HospitalEntity> optional = this.basicDetailRepository.getHospitalById( hospitalId );
		return optional.map( hospital -> _saveServiceGroupAndUpdateHospital( serviceGroup, hospital) );
	}

	@Override
	public List< HospitalServiceGroup > searchByCriteria(Specification< HospitalServiceGroup > specification) {
		return this.serviceGroupDataRepository.findAll(specification);
	}

	@Override
	public List< HospitalServiceGroup > getByHospital(Long hospitalId) {
		Optional< HospitalEntity > optional = this.basicDetailRepository.getHospitalById(hospitalId);
		return optional.map( this.serviceGroupDataRepository::findByHospital ).orElse( new ArrayList<>() );
	}

	private HospitalServiceGroup _saveServiceGroupAndUpdateHospital( HospitalServiceGroup serviceGroup, HospitalEntity hospital ){
		HospitalServiceGroup saved = this.serviceGroupDataRepository.save( setServiceGroup(serviceGroup, hospital ) );
		this.basicDetailRepository.updateServiceGroup( hospital, saved );
		return saved;
	}

	private HospitalServiceGroup setServiceGroup( HospitalServiceGroup serviceGroup, HospitalEntity hospital ){
		HospitalServiceGroup hospitalServiceGroup = hospital.getServiceGroup() != null ? hospital.getServiceGroup() : new HospitalServiceGroup();
		hospitalServiceGroup.setIsEnabled( serviceGroup.getIsEnabled() );
		hospitalServiceGroup.setTitle( serviceGroup.getTitle() );
		hospitalServiceGroup.setHospital( hospital );
		return hospitalServiceGroup;
	}

}
