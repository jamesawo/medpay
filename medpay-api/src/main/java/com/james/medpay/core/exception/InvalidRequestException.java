package com.james.medpay.core.exception;

public class InvalidRequestException extends RuntimeException {
	public InvalidRequestException( String errorMessage ) {
		super( errorMessage );
	}
}
