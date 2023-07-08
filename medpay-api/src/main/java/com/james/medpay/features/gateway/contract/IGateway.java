package com.james.medpay.features.gateway.contract;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface IGateway {
	ResponseEntity<Map<String, String>> generatePaymentReference(GatewayRequest request);

}
