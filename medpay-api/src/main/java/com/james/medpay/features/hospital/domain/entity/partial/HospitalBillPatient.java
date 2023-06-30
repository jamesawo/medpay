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
public class HospitalBillPatient {
	private String phoneNumber;
	private String fullName;
	private String patientNumber;

	public static HospitalBillPatient fromRequest( Map<String, Object> map ) {
		HospitalBillPatient billPatient = new HospitalBillPatient();
		billPatient.setPhoneNumber( "N/A" );
		billPatient.setFullName( getValue( map.get( "patientFullName" ) ) );
		billPatient.setPatientNumber( getValue( map.get( "patientNumber" ) ) );
		return billPatient;
	}

	private static String getValue( Object fromMap ) {
		return isNotEmpty( fromMap ) ? format( "%s", fromMap ) : "";
	}

}
