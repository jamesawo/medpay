package com.james.medpay.features.hospital.data.datasource.contract;

import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import org.springframework.http.ResponseEntity;

public interface IHospitalApiDataSourceIntegrationContract {

	ResponseEntity<?> getBillDetailsFromApi( String billNumber, HospitalEntity hospital );

	ResponseEntity<?> sendBillIsPaidNotificationToApi( String billNumber, HospitalEntity hospital );

	ResponseEntity<?> getBillReceiptFromApi( String billNumber, HospitalEntity hospital );

	ResponseEntity<?> getBillPaymentStatusFromApi( String billNumber, HospitalEntity hospital );

	ResponseEntity<?> sendPingRequestToApiConnection( HospitalEntity hospital );
}
