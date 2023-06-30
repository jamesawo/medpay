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
public class HospitalBill {
	private String date;
	private String billNumber;
	private boolean hasTotal = false;
	private boolean hasPatient = false;
	private boolean hasItems = false;
	private boolean hasCashier = false;
	private HospitalBillTotal total = null;
	private HospitalBillPatient patient = null;
	private List<HospitalBillItemDetail> items = new ArrayList<>();
	private HospitalBillCashierDetail cashier = null;

	public static HospitalBill fromResponse( Map<String, Object> body ) {
		HospitalBill hospitalBill = new HospitalBill();
		hospitalBill.setDate( toDateString( body.get( "costDate" ) ) );
		hospitalBill.setBillNumber( getValue( body.get( "invoiceNumber" ) ) );
		setCashierDetailsIfPresent( body, hospitalBill );
		setPatientDetailsIfPresent( body, hospitalBill );
		setBillItemsIfPresent( body, hospitalBill );
		setBillTotalIfPresent( body, hospitalBill );
		return hospitalBill;
	}

	private static String toDateString( Object costDate ) {
		if ( isNotEmpty( costDate ) ) {
			Map<String, Object> dateMap = (Map<String, Object> ) costDate;
			String year = getValue( dateMap.get( "year" ) );
			String month = getValue( dateMap.get( "month" ) );
			String day = getValue( dateMap.get( "day" ) );
			return String.format( "%s-%s-%s", year, month, day );
		}
		return "";
	}

	private static void setBillTotalIfPresent( Map<String, Object> map, HospitalBill hospitalBill ) {
		Map<String, Object> billTotal = ( Map ) map.get( "billTotal" );
		if ( isNotEmpty( billTotal ) ) {
			hospitalBill.setTotal( HospitalBillTotal.fromResponse( billTotal ) );
			hospitalBill.hasTotal = true;
		}
	}

	private static void setCashierDetailsIfPresent( Map<String, Object> map, HospitalBill hospitalBill ) {
		Map<String, Object> costByDto = ( Map ) map.get( "costByDto" );
		Map<String, Object> locationDto = ( Map ) map.get( "locationDto" );
		if ( isNotEmpty( costByDto ) && isNotEmpty( locationDto ) ) {
			hospitalBill.setCashier( HospitalBillCashierDetail.fromResponse( costByDto, locationDto ) );
			hospitalBill.hasCashier = true;
		}
	}

	private static void setPatientDetailsIfPresent( Map<String, Object> map, HospitalBill hospitalBill ) {
		Object patientDetailDto = map.get( "patientDetailDto" );
		if ( isNotEmpty( patientDetailDto ) ) {
			hospitalBill.setPatient( HospitalBillPatient.fromRequest( ( Map<String, Object> ) patientDetailDto ) );
			hospitalBill.hasPatient = true;
		}
	}

	private static void setBillItemsIfPresent( Map<String, Object> map, HospitalBill hospitalBill ) {
		Object billItems = map.get( "billItems" );
		if ( isNotEmpty( billItems ) ) {
			hospitalBill.setItems( HospitalBillItemDetail.toList( ( List<Map<String, Object>> ) billItems ) );
			hospitalBill.hasItems = true;
		}
	}

	private static String getValue( Object fromMap ) {
		return isNotEmpty( fromMap ) ? format( "%s", fromMap ) : "";
	}

}
