
package com.james.medpay.features.hospital.domain.usecase.basicDetail;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalBasicDetailRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class HospitalGetHospitalCompleteDetailUsecase implements UsecaseContract<Optional<HospitalEntity>, Long> {

	private final IHospitalBasicDetailRepository repository;

	/**
	 * When called should return complete hospital details
	 * <p>
	 *
	 * @param hospitalId
	 * @return Optional<HospitalEntity>
	 */
	@Override
	public Optional<HospitalEntity> execute( Long hospitalId ) {
		return repository.getHospitalById( hospitalId );
	}
}
