package com.james.medpay.core.exception;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class LoggerHandler {

	static private String getActiveProfile() {
		String message = System.getProperty( "spring.profiles.active", "unknown" );
		return message;
	}

	static public void logError( String message, Object error ) {
		if ( Objects.equals( getActiveProfile(), "prod" ) ) {
			log.error( message, error );
		}
	}

	static public void logInfo( String message, Object error ) {
		if ( Objects.equals( getActiveProfile(), "prod" ) ) {
			log.info( message, error );
		}
	}
}
