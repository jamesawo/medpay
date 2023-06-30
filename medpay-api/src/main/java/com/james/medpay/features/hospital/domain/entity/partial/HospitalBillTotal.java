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
public class HospitalBillTotal {
	private String netAmount;
	private String discountAmount;
	private String grossAmount;

	public static HospitalBillTotal fromResponse( Map<String, Object> map ) {
		HospitalBillTotal billTotal = new HospitalBillTotal();
		billTotal.setDiscountAmount( getValue(map.get( "discountTotal" )) );
		billTotal.setGrossAmount( getValue(map.get( "grossTotal" )) );
		billTotal.setNetAmount( getValue(map.get( "netTotal" ))  );
		return billTotal;
	}

	private static String getValue( Object fromMap ) {
		return isNotEmpty( fromMap ) ? format( "%s",fromMap ): "";
	}
}
