package com.james.medpay.features.hospital.data.repositoryImp;

import com.james.medpay.core.annotation.RepositoryImpl;
import com.james.medpay.core.exception.ApplicationException;
import com.james.medpay.core.exception.InvalidRequestException;
import com.james.medpay.features.hospital.data.datasource.contract.IHospitalApiDataSourceIntegrationContract;
import com.james.medpay.features.hospital.data.datasource.implementation.HospitalHealthSuitApiDatasource;
import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.features.hospital.domain.entity.partial.HospitalBill;
import com.james.medpay.features.hospital.domain.repository.contract.IHospitalApiUsageRepository;
import com.james.medpay.features.hospital.domain.usecase.basicDetail.HospitalGetHospitalCompleteDetailUsecase;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

import static java.util.Optional.of;

@RepositoryImpl
public class HospitalApiUsageRepositoryImpl implements IHospitalApiUsageRepository {

	private final HospitalGetHospitalCompleteDetailUsecase getHospitalCompleteDetailUsecase;
	private final IHospitalApiDataSourceIntegrationContract apiDataSourceIntegration;

	public static final String SEARCH_BILL_FAILED = "ERROR, BILL SEARCH FAILED.";

	public HospitalApiUsageRepositoryImpl(
			HospitalGetHospitalCompleteDetailUsecase getHospitalCompleteDetailUsecase,
			HospitalHealthSuitApiDatasource healthSuitApiDatasource
	) {
		this.getHospitalCompleteDetailUsecase = getHospitalCompleteDetailUsecase;
		this.apiDataSourceIntegration = healthSuitApiDatasource;
	}

	@Override
	public Optional<HospitalBill> apiSearchForBillDetail( Long hospitalId, String billNumber ) {
		Optional<HospitalEntity> optional = getHospitalCompleteDetailUsecase.execute( hospitalId );
		return optional.map( hospital -> _getHospitalBillFromApiDataSource( billNumber, hospital ) );
	}

	private HospitalBill _getHospitalBillFromApiDataSource( String bill, HospitalEntity hospital ) {
		this._checkIfHospitalHasSoftwareIsEnabledOrThrow( hospital );
		ResponseEntity<?> response = apiDataSourceIntegration.getBillDetailsFromApi( bill, hospital );
		return this._getRequestBodyFromResponseOrThrow( response );
	}

	private void _checkIfHospitalHasSoftwareIsEnabledOrThrow( HospitalEntity hospital ) {
		if ( !hospital.getHasHospitalSoftware() ) {
			throw new InvalidRequestException( "Hospital 'HasSoftware' must be enabled to use this feature" );
		}
	}

	private HospitalBill _getRequestBodyFromResponseOrThrow( ResponseEntity<?> response ) {
		if ( response.getStatusCode().is2xxSuccessful() && response.getBody() != null ) {
			Map<String, Object> body = ( Map<String, Object> ) response.getBody();
			return HospitalBill.fromResponse( body );
		}
		throw new ApplicationException( SEARCH_BILL_FAILED );
	}

	@Override
	public Optional<Boolean> apiSendBillPayment( Long hospitalId, String billNumber ) {
		Optional<HospitalEntity> optional = getHospitalCompleteDetailUsecase.execute( hospitalId );
		return optional.map( hospital -> _sendPaymentNotifToApi( billNumber, hospital ) ).orElseGet( () -> of( false ) );
	}

	private Optional<Boolean> _sendPaymentNotifToApi( String bill, HospitalEntity hospital ) {

		//todo:: if request failed, send error message from response back to client
		ResponseEntity<?> response = apiDataSourceIntegration.sendBillIsPaidNotificationToApi( bill, hospital );
		if ( response.getStatusCode().is2xxSuccessful() && response.getBody() != null ) {
			return of( ( boolean ) response.getBody() );
		}
		return of( false );
	}

	@Override
	public Optional<Boolean> apiCheckPaymentStatus( Long hospitalId, String billNumber ) {
		return Optional.empty();
	}

	@Override
	public Optional<Boolean> apiCheckIsConnectionAlive( Long hospitalId ) {
		return Optional.empty();
	}
}
