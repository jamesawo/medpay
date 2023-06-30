
package com.james.medpay.features.hospital.domain.usecase.basicDetail;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalBasicDetailRepository;

import java.util.Optional;

@UseCase
public class HospitalGetHospitalBasicDetailUsecase implements UsecaseContract<Optional<HospitalEntity>, Long> {

	private final IHospitalBasicDetailRepository repository;

	public HospitalGetHospitalBasicDetailUsecase( IHospitalBasicDetailRepository repositoryImpl ) {
		this.repository = repositoryImpl;
	}


	/**
	 * When called should only attach the HospitalDetailEntity to
	 * <p>
	 * the HospitalEntity along with the main hospital properties
	 *
	 * @param hospitalId
	 * @return Optional<HospitalEntity>
	 */
	@Override
	public Optional<HospitalEntity> execute( Long hospitalId ) {
		return repository.getHospitalByIdWithDetailOnly( hospitalId );
	}
}
