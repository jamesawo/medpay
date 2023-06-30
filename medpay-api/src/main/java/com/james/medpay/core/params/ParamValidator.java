package com.james.medpay.core.params;

import com.james.medpay.core.exception.InvalidRequestException;
import org.apache.commons.lang3.ObjectUtils;

public class ParamValidator {
	public static final String INVALID_PARAM = "Invalid Request Parameter Supplied";
	public static final String HOSPITAL_ID = "Hospital ID";
	public static final String HOSPITAL_DETAIL = "Hospital Details";
	public static final String ACTIVE_ENV = "Active Environment";
	public static final String GROUP_ID = "Group Id";
	public static final String REVENUE_HEAD = "Revenue Head";
	public static final String API_CONFIG = "Api Configuration";

	public static void _throwInvalidRequestExceptionIfParamHasAnyNullProps(
			Param<?> param, String longPropertyName, String modelPropertyName
	) {
		if ( ObjectUtils.isEmpty( param.getId() ) ) {
			throw new InvalidRequestException( _getErrorMessage( longPropertyName ) );
		}

		if ( ObjectUtils.isEmpty( param.getModel() ) ) {
			throw new InvalidRequestException( _getErrorMessage( modelPropertyName ) );
		}
	}

	private static String _getErrorMessage( String propertyName ) {
		return String.format( "ERROR, %s IS INVALID", propertyName );
	}
}
