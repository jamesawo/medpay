package com.james.medpay.core.exception;

public class ApplicationException extends RuntimeException {
	public ApplicationException( String errorMessage) {
		super( errorMessage );
	}
}
