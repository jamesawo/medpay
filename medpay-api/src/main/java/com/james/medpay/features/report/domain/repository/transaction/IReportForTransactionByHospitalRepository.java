package com.james.medpay.features.report.domain.repository.transaction;

import com.james.medpay.features.report.data.request.TransactionReportRequest;

public interface IReportForTransactionByHospitalRepository {

	byte[] getCollectionReportByHospital( TransactionReportRequest request );

}
