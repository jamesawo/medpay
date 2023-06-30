
package com.james.medpay.features.report.data.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Data
@Setter @Getter
public class TransactionServiceGroupPayload {
	private String groupTitle;
	private JRBeanCollectionDataSource transactions; //TransactionServicePayload
}
