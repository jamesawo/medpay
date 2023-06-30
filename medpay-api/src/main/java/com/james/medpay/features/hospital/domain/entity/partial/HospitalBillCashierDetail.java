package com.james.medpay.features.hospital.domain.entity.partial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

import static java.lang.String.format;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HospitalBillCashierDetail {
	private String name;
	private String location;

	public static HospitalBillCashierDetail fromResponse( Map<String, Object> cashierMap, Map<String, Object> locationMap ) {
		HospitalBillCashierDetail detail = new HospitalBillCashierDetail();
		detail.setName( getValue( cashierMap.get( "label" ) ) );
		detail.setLocation( getValue( locationMap.get( "name" ) ) );
		return detail;
	}

	private static String getValue( Object fromMap ) {
		return isNotEmpty( fromMap ) ? format( "%s",fromMap ): "";
	}
}
