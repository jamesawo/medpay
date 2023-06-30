package com.james.medpay.features.transaction.domain.entity.enums;

public enum TransactionStatusEnum {
	// initiating a new transaction
	INITIATED,
	// pending if the transaction status is yet to be verified on payment gateway
	PENDING,
	// successful if all verification was successful.
	SUCCESSFUL,
	// cancelled if the transaction was cancelled at any time
	CANCELLED,
	// failed, should have a reason
	FAILED
}
