
package com.james.medpay.features.report.data.interactor.implementation;

import com.james.medpay.core.annotation.Interactor;
import com.james.medpay.features.report.data.interactor.contract.ITransactionReportInteractor;
import com.james.medpay.features.report.data.request.TransactionReportRequest;
import com.james.medpay.features.report.domain.usecase.transaction.ReportGetForTransactionByAgentUsecase;
import com.james.medpay.features.report.domain.usecase.transaction.ReportGetForTransactionByHospitalUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.ResponseEntity.ok;

@Interactor
@RequiredArgsConstructor
public class TransactionReportInteractorImpl implements ITransactionReportInteractor {

	private final ReportGetForTransactionByHospitalUsecase byHospitalUsecase;
	private final ReportGetForTransactionByAgentUsecase byAgentUsecase;

	@Override
	public ResponseEntity<byte[]> getDailyCollectionReport(TransactionReportRequest request ) {
		byte[] report = this.byHospitalUsecase.execute( request );
		return ok().body( report );
	}

	@Override
	public ResponseEntity<byte[]> getAgentCollectionReport( TransactionReportRequest request ) {
		byte[] report = this.byAgentUsecase.execute( request );
		return ok().body( report );
	}

}
