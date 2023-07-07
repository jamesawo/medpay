package com.james.medpay.features.gateway.contract;

public interface IGateway {
	GatewayResponse generatePaymentReference(GatewayRequest request);


}
