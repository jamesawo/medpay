
package com.james.medpay.features.transaction.domain.entity.contract;

public interface ITransactionPayerDetail {

	Long getId();

	void setId( Long id );

	String getFullName();

	void setFullName( String fullName );

	String getPhoneNumber();

	void setPhoneNumber( String phoneNumber );

	String getPatientNumber();

	void setPatientNumber( String patientNumber );

	String getPayerOthers();

	void setPayerOthers( String payerOthers );

}
