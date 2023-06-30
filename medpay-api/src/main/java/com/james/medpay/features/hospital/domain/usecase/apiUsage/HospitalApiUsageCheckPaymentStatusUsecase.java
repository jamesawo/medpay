
package com.james.medpay.features.hospital.domain.usecase.apiUsage;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.hospital.data.repositoryImp.HospitalApiUsageRepositoryImpl;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class HospitalApiUsageCheckPaymentStatusUsecase implements UsecaseContract<Optional<Boolean>, Param<String>> {

	private final HospitalApiUsageRepositoryImpl apiUsageRepository;


	@Override
	public Optional<Boolean> execute( Param<String> param ) {
		return apiUsageRepository.apiCheckPaymentStatus( param.getId(), param.getModel() );
	}
}
