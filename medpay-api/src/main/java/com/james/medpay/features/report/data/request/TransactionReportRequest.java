package com.james.medpay.features.report.data.request;

import com.james.medpay.core.params.DateRange;
import com.james.medpay.features.hospital.data.request.HospitalRequest;
import com.james.medpay.features.hospital.data.request.HospitalRevenueHeadRequest;
import com.james.medpay.features.report.data.request.enums.ReportFormatEnum;
import com.james.medpay.features.report.data.request.enums.TransactionReportGroupEnum;
import com.james.medpay.features.transaction.domain.entity.enums.TransactionStatusEnum;
import com.james.medpay.features.users.data.request.UserRequest;
import lombok.Data;

@Data
public class TransactionReportRequest {
	private ReportFormatEnum format;
	private HospitalRequest hospital;
	private UserRequest user;
	private DateRange dateRange;
	private HospitalRevenueHeadRequest revenueHead;
	private TransactionReportGroupEnum groupBy;
	private TransactionStatusEnum status;
}
