package com.james.medpay.features.authentication.domain.entity.contract;

import com.james.medpay.features.authentication.domain.entity.enums.AuthModuleEnum;


public interface IPermission {

	Long getId();

	void setId( Long id );

	String getName();

	void setName( String name );

	AuthModuleEnum getModule();

	void setModule( AuthModuleEnum module );

}
