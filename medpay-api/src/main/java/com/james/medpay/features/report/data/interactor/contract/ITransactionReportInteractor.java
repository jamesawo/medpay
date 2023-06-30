
package com.james.medpay.features.report.data.interactor.contract;

import com.james.medpay.features.report.data.request.TransactionReportRequest;
import org.springframework.http.ResponseEntity;

public interface ITransactionReportInteractor {

	ResponseEntity<byte[]> getDailyCollectionReport( TransactionReportRequest request );

	ResponseEntity<byte[]> getAgentCollectionReport(TransactionReportRequest request);

}
