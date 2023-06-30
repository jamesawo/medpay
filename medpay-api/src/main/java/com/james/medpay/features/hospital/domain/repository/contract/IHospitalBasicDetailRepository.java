package com.james.medpay.features.hospital.domain.repository.contract;

import com.james.medpay.features.hospital.domain.entity.HospitalDetail;
import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.hospital.domain.entity.HospitalServiceGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface IHospitalBasicDetailRepository {
	List<HospitalEntity> getAllHospitals();

	Page<HospitalEntity> getHospitalsInPages();

	HospitalEntity createHospital( HospitalEntity hospital );

	Optional<HospitalEntity> getHospitalById( Long id );

	Optional<HospitalEntity> getHospitalByIdWithDetailOnly( Long id );

	Optional<HospitalDetail> updateHospitalDetails( Param<HospitalDetail> params );

	List<HospitalEntity> getHospitalsWithSearchRequest( Specification<HospitalEntity>  spec);

	Optional<HospitalEntity> updatePartialDetails( Param<HospitalEntity> param );

	HospitalEntity updateServiceGroup( HospitalEntity hospital, HospitalServiceGroup serviceGroup );

}
