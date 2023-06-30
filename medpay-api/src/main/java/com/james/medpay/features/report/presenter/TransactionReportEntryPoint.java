
package com.james.medpay.features.report.presenter;

import com.james.medpay.features.report.data.interactor.contract.ITransactionReportInteractor;
import com.james.medpay.features.report.data.request.TransactionReportRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.james.medpay.core.constant.Routes.API_PREFIX;

@RestController
@RequestMapping( value = API_PREFIX + "/report-transaction" )
@RequiredArgsConstructor
public class TransactionReportEntryPoint {

	private final ITransactionReportInteractor interactor;

	@PostMapping(value = "/daily-collection-report")
	private ResponseEntity<byte[]> getDailyCollectionReport( @RequestBody TransactionReportRequest searchPayload ){
		return this.interactor.getDailyCollectionReport( searchPayload );
	}

	@PostMapping(value = "/agent-collection-report")
	private ResponseEntity<byte[]> getAgentCollectionReport( @RequestBody TransactionReportRequest searchPayload ){
		return this.interactor.getAgentCollectionReport( searchPayload );
	}

}
