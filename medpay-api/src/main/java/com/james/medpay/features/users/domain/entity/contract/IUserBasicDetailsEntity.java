package com.james.medpay.features.users.domain.entity.contract;

import com.james.medpay.features.users.domain.entity.enums.GenderEnum;
import com.james.medpay.features.users.domain.entity.enums.UserPreferredNameEnum;

public interface IUserBasicDetailsEntity {

	Long getId();

	void setId( Long id );

	String getPhoneNumber();

	void setPhoneNumber( String phoneNumber );

	String getFirstName();

	void setFirstName( String firstName );

	String getLastName();

	void setLastName( String lastName );

	String getOtherName();

	void setOtherName( String otherName );

	GenderEnum getGender();

	void setGender( GenderEnum gender );

	String getPreferredPronoun();

	void setPreferredPronoun( String preferredPronoun );

	UserPreferredNameEnum getPreferredName();

	void setPreferredName( UserPreferredNameEnum preferredName );

	String getSocialFb();

	void setSocialFb( String socialFb );

	String getSocialIg();

	void setSocialIg( String socialIg );

	String getSocialLkd();

	void setSocialLkd( String socialLkd );

	String getPersonalStatement();

	void setPersonalStatement( String personalStatement );

	String getEmergencyPhone();

	void setEmergencyPhone( String emergencyPhone );
}
