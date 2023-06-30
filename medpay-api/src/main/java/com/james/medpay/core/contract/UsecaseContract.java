package com.james.medpay.core.contract;

public interface UsecaseContract<Type, Param> {
	Type execute( Param param );
}
