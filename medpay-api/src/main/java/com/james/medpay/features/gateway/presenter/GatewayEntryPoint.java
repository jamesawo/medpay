/*
 * @Author: james.junior
 * @Date: 7/7/23 12:56
 *
 * @Project: medpay-api
 */

package com.james.medpay.features.gateway.presenter;

import com.james.medpay.features.gateway.contract.IGateway;
import com.james.medpay.features.gateway.implementation.Remitta.RemittaGateway;
import com.james.medpay.features.gateway.implementation.Remitta.Request.Remitta_Transaction_Request;
import com.james.medpay.features.transaction.data.request.TransactionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.james.medpay.core.constant.Routes.API_PREFIX;

@RestController
@RequestMapping( value = API_PREFIX + "/gateway" )
public class GatewayEntryPoint {
	private final IGateway gateway;

	public GatewayEntryPoint(RemittaGateway gateway) {
		this.gateway = gateway;
	}

	@PostMapping("generate-rrr")
	public ResponseEntity<Map<String, String>> generateRRR(@RequestBody TransactionRequest transactionRequest){
		Remitta_Transaction_Request request = new Remitta_Transaction_Request(transactionRequest);
		return this.gateway.generatePaymentReference(request);
	}
}
