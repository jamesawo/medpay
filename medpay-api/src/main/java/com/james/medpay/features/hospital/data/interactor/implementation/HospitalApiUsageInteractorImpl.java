package com.james.medpay.features.hospital.data.interactor.implementation;

import com.james.medpay.core.annotation.Interactor;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.hospital.data.interactor.contract.HospitalApiUsageInteractor;
import com.james.medpay.features.hospital.domain.entity.partial.HospitalBill;
import com.james.medpay.features.hospital.domain.usecase.apiUsage.HospitalApiUsageCheckIsConnectionAliveUsecase;
import com.james.medpay.features.hospital.domain.usecase.apiUsage.HospitalApiUsageCheckPaymentStatusUsecase;
import com.james.medpay.features.hospital.domain.usecase.apiUsage.HospitalApiUsageSearchBillUsecase;
import com.james.medpay.features.hospital.domain.usecase.apiUsage.HospitalApiUsageSendPaymentSuccessNotifUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@Interactor
@RequiredArgsConstructor
public class HospitalApiUsageInteractorImpl implements HospitalApiUsageInteractor {

	private final HospitalApiUsageCheckIsConnectionAliveUsecase checkIsConnectionAliveUsecase;
	private final HospitalApiUsageCheckPaymentStatusUsecase checkPaymentStatusUsecase;
	private final HospitalApiUsageSendPaymentSuccessNotifUsecase sendPaymentSuccessNotifUsecase;
	private final HospitalApiUsageSearchBillUsecase searchBillUsecase;


	@Override
	public ResponseEntity<Optional<HospitalBill>> apiBillLookup( Long hospitalId, String billNumber ) {
		Optional<HospitalBill> optional = this.searchBillUsecase.execute( new Param<>( hospitalId, billNumber ) );
		return ok().body( optional );
	}

	@Override
	public ResponseEntity<Optional<Boolean>> apiSendPaymentSuccessNotification( Long hospitalId, String billNumber ) {
		Optional<Boolean> optional = this.sendPaymentSuccessNotifUsecase.execute( new Param<>( hospitalId, billNumber ) );
		return ok().body( optional );
	}

	@Override
	public ResponseEntity<Optional<Boolean>> apiCheckPaymentStatus( Long hospitalId, String billNumber ) {
		Optional<Boolean> optional = this.checkPaymentStatusUsecase.execute( new Param<>( hospitalId, billNumber ) );
		return ok().body( optional );
	}

	@Override
	public ResponseEntity<Optional<Boolean>> apiCheckIsConnectionAlive( Long hospitalId ) {
		Optional<Boolean> optional = this.checkIsConnectionAliveUsecase.execute( hospitalId );
		return ok().body( optional );
	}
}
