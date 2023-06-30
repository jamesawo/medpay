
package com.james.medpay.features.users.domain.entity.contract;

import com.james.medpay.features.authentication.domain.entity.Role;
import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.features.users.domain.entity.UserAddressEntity;
import com.james.medpay.features.users.domain.entity.UserBasicDetailsEntity;
import com.james.medpay.features.users.domain.entity.UserConfigurationEntity;
import com.james.medpay.features.users.domain.entity.enums.TrimPatternEnum;
import com.james.medpay.features.users.domain.entity.enums.UserTypeEnum;

import java.time.LocalDate;
import java.util.Collection;

public interface IUserEntity {

	Long getId();

	void setId( Long id );

	String getNickName();

	void setNickName( String nickName );

	String getEmail();

	void setEmail( String email );

	String getPassword();

	void setPassword( String password );

	String getUuid();

	void setUuid( String uuid );

	UserConfigurationEntity getConfiguration();

	void setConfiguration( UserConfigurationEntity configuration );

	UserAddressEntity getAddress();

	void setAddress( UserAddressEntity address );

	UserAddressEntity getEmergencyAddress();

	void setEmergencyAddress( UserAddressEntity emergencyAddress );

	UserTypeEnum getUserTypeEnum();

	void setUserTypeEnum( UserTypeEnum userTypeEnum );

	UserBasicDetailsEntity getBasicDetails();

	void setBasicDetails( UserBasicDetailsEntity userBasicDetails );

	Boolean getIsEnabled();

	void setIsEnabled( Boolean isEnabled );

	LocalDate getExpiryDate();

	void setExpiryDate( LocalDate expiryDate );

	HospitalEntity getHospital();

	void setHospital( HospitalEntity hospital );

	boolean isAccountExpired();

	void setRoles( Collection<Role> roles );

	Collection<Role> getRoles();

	boolean isNotSuperUser();

	void setProfilePicUrl(String profilePicUrl);

	String getProfilePicUrl();

	void trim( TrimPatternEnum pattern );

	String getFullName();

	String getPhoneNumber();
}
