
package com.james.medpay.features.transaction.domain.entity.contract;

import com.james.medpay.features.hospital.domain.entity.HospitalService;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalServiceCategoryEnum;

import java.util.List;

public interface ITransactionPaymentDetail {

	Long getId();

	void setId( Long id );

	String getBillNumber();

	void setBillNumber( String billNumber );

	HospitalServiceCategoryEnum getCategory();

	void setCategory( HospitalServiceCategoryEnum category );

	String getOther();

	void setOther( String other );

	List<HospitalService> getServices();

	void setServices(List<HospitalService> services);

	void trim();
}
