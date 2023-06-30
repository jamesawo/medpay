package com.james.medpay.core.contract;

public interface DataImporter<Type, Data> {
	Type export( Data dataToImport );
}
