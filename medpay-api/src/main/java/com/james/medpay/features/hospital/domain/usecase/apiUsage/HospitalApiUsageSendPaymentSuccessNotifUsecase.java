
package com.james.medpay.features.hospital.domain.usecase.apiUsage;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalApiUsageRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class HospitalApiUsageSendPaymentSuccessNotifUsecase implements UsecaseContract<Optional<Boolean>, Param<String>> {

	private final IHospitalApiUsageRepository repository;
	
	@Override
	public Optional<Boolean> execute( Param<String> param ) {
		return repository.apiSendBillPayment( param.getId(), param.getModel() );
	}
}
