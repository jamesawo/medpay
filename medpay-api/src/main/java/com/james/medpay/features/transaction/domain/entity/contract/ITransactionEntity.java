
package com.james.medpay.features.transaction.domain.entity.contract;

import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.features.transaction.domain.entity.TransactionPayerDetail;
import com.james.medpay.features.transaction.domain.entity.TransactionPaymentDetail;
import com.james.medpay.features.transaction.domain.entity.TransactionSettlement;
import com.james.medpay.features.transaction.domain.entity.enums.TransactionStatusEnum;
import com.james.medpay.features.users.domain.entity.UserEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public interface ITransactionEntity {
	Long getId();

	void setId( Long id );

	String getUuid();

	void setUuid( String uuid );

	LocalDate getDate();

	void setDate( LocalDate date );

	LocalTime getTime();

	void setTime( LocalTime time );

	BigDecimal getAmount();

	void setAmount( BigDecimal amount );

	Integer getNotificationCount();

	void setNotificationCount( Integer notificationCount );

	Boolean getHasNotifiedHospital();

	void setHasNotifiedHospital( Boolean hasNotifiedHospital );

	HospitalEntity getHospital();

	void setHospital( HospitalEntity hospital );

	UserEntity getUser();

	void setUser( UserEntity user );

	TransactionStatusEnum getStatusEnum();

	void setStatusEnum( TransactionStatusEnum statusEnum );

	TransactionPaymentDetail getPaymentDetail();

	void setPaymentDetail( TransactionPaymentDetail paymentDetail );

	TransactionPayerDetail getPayerDetail();

	void setPayerDetail( TransactionPayerDetail payerDetail );

	TransactionSettlement getSettlement();

	void setSettlement( TransactionSettlement settlement );

	String getTransactionOthers();

	void setTransactionOthers( String transactionOthers );

	void setFields();

	void incrementNotificationCount();

	void trim();

	String getAgentFullName();
}
