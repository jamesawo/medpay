package com.james.medpay.features.hospital.domain.entity.partial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HospitalBillItemDetail {
	private String quantity;
	private String description;
	private String amount;

	public static List<HospitalBillItemDetail> toList( List<Map<String, Object>> mapList ) {
		List<HospitalBillItemDetail> list = new ArrayList<>();
		if ( isNotEmpty( mapList ) && !mapList.isEmpty() ) mapList.forEach( map -> _addMapValueToItemList( map, list ) );
		return list;
	}

	private static void _addMapValueToItemList( Map<String, Object> map, List<HospitalBillItemDetail> list ) {
		HospitalBillItemDetail detail = new HospitalBillItemDetail();
		detail.setAmount( getValue( map.get( "netAmount" ) ) );
		detail.setDescription( getValue( map.get( "description" ) ) );
		detail.setQuantity( getValue( map.get( "quantity" ) ) );
		list.add( detail );
	}

	private static String getValue( Object fromMap ) {
		return isNotEmpty( fromMap ) ? format( "%s", fromMap ) : "";
	}
}
