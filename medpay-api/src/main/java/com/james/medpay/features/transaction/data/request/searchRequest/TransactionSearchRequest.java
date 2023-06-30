package com.james.medpay.features.transaction.data.request.searchRequest;

import com.james.medpay.core.params.DateRange;
import com.james.medpay.features.hospital.data.request.HospitalRequest;
import com.james.medpay.features.transaction.domain.entity.enums.TransactionStatusEnum;
import com.james.medpay.features.users.data.request.UserRequest;
import lombok.Data;

@Data
public class TransactionSearchRequest {
	private HospitalRequest hospital;
	private UserRequest user;
	private String reference;
	private String serial;
	private String billNumber;
	private TransactionStatusEnum status;
	private DateRange dateRange;
	private String payer;
}

