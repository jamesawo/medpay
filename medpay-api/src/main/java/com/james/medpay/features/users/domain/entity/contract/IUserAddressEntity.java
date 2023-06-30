
package com.james.medpay.features.users.domain.entity.contract;

public interface IUserAddressEntity {

	Long getId();

	void setId( Long id );

	String getState();

	void setState( String state );

	String getCity();

	void setCity( String city );

	String getStreet();

	void setStreet( String street );

	String getCurrentPlaceOfResidence();

	void setCurrentPlaceOfResidence( String currentPlaceOfResidence );

	Boolean getIsVerified();

	void setIsVerified( Boolean verified );
}
