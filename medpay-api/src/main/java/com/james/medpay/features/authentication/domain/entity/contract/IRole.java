package com.james.medpay.features.authentication.domain.entity.contract;

import com.james.medpay.features.authentication.domain.entity.Permission;

import java.util.Set;

public interface IRole {

	Long getId();

	void setId( Long id );

	String getUuid();

	void setUuid( String uuid );

	String getName();

	void setName( String name );

	String getDescription();

	void setDescription( String description );

	Set<Permission> getPermissions();

	void setPermissions( Set<Permission> permissions );

	void setUUID();

	Boolean getStatus();

	void setStatus( Boolean status );

	boolean isNotSuperRole();

	void trim();
}
