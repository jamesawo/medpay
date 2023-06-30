package com.james.medpay.features.hospital.data.repositoryImp;

import com.james.medpay.core.annotation.RepositoryImpl;
import com.james.medpay.core.params.BiParam;
import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalStateCheckerRepository;
import com.james.medpay.features.hospital.domain.usecase.basicDetail.HospitalGetHospitalCompleteDetailUsecase;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RepositoryImpl
@RequiredArgsConstructor
public class HospitalStateCheckerRepositoryImpl implements IHospitalStateCheckerRepository {
	private final HospitalGetHospitalCompleteDetailUsecase getCompleteDetailUsecase;


	@Override
	public Optional<Boolean> isHospitalConfigured( Long hospitalId ) {
		Optional<HospitalEntity> optional = getCompleteDetailUsecase.execute( hospitalId );
		return optional.map( hospital -> {
			BiParam<Boolean, List<String>> checkConfiguration =
					hospital.getApiConfiguration().checkConfiguration();
			return checkConfiguration.getLeft();
		} );
	}

	@Override
	public Optional<Map<String, Object>> runHospitalStateChecker(
			Long hospitalId
	) {
		Optional<HospitalEntity> optional = getCompleteDetailUsecase.execute( hospitalId );
		return optional.map( HospitalEntity::runStateChecker );
	}

}
