package com.james.medpay.core.contract;

public interface DataExporter<Type, Data> {
	Type export( Data dataToExport );
}
