
package com.james.medpay.features.report.data.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter @Getter
public class TransactionServicePayload {
	private Double amount;
	private String time;
	private String date;
	private String agentFullName;
	private String payerFullName;
}
