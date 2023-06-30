
package com.james.medpay.features.transaction.domain.entity.contract;

import java.math.BigDecimal;

public interface ITransactionSettlement {

	Long getId();

	void setId( Long id );

	BigDecimal getSplit();

	void setSplit( BigDecimal split );

	String getSplitType();

	void setSplitType( String splitType );
}
