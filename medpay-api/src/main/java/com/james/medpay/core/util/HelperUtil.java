/*
 * @Author: james.junior
 * @Date: 10/25/22 7:35 PM
 *
 * @Project: paymed-api
 */

package com.james.medpay.core.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

public class HelperUtil {

	public static String formatDate( LocalDate date) {
		return isNotEmpty( date ) ? date.format( DateTimeFormatter.ofLocalizedDate( FormatStyle.MEDIUM ) ) : "";
	}

	public static String formatTime( LocalTime time ) {
		return isNotEmpty( time ) ? time.format( DateTimeFormatter.ofPattern( "KK:mm:ss a", Locale.ENGLISH  ) ) : "";
	}

	public static String formatAmount( Double amount ) {
		return String.format( "%,.2f", amount );
	}


}
