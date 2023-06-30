package com.james.medpay.core.configuration.config;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

public class CustomLocalDateConverter implements Converter<String, LocalDate> {

	@Override
	public LocalDate convert( String from ) {
		return LocalDate.parse( from );
	}

}
