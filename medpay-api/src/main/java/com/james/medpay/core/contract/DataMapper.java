package com.james.medpay.core.contract;

public interface DataMapper<Type, Param> {
	Type toEntity( Param param );

	Param toRequest( Type type );
	
}
