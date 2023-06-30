
package com.james.medpay.features.report.domain.usecase.transaction;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.core.contract.UsecaseContract;
import com.james.medpay.features.report.data.request.TransactionReportRequest;
import com.james.medpay.features.report.domain.repository.transaction.IReportForTransactionByHospitalRepository;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ReportGetForTransactionByHospitalUsecase implements UsecaseContract<byte[], TransactionReportRequest> {

	private final IReportForTransactionByHospitalRepository repository;

	@Override
	public byte[] execute( TransactionReportRequest request ) {
		return this.repository.getCollectionReportByHospital( request );
	}
}
