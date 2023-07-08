/*
 * @Author: james.junior
 * @Date: 7/7/23 12:52
 *
 * @Project: medpay-api
 */

package com.james.medpay.features.gateway.implementation.Remitta;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.james.medpay.features.gateway.contract.IGateway;
import com.james.medpay.features.gateway.contract.GatewayRequest;
import com.james.medpay.features.gateway.implementation.Remitta.Request.Remitta_Demo_RRR;
import com.james.medpay.features.gateway.implementation.Remitta.Request.Remitta_Payment_Info;
import com.james.medpay.features.gateway.implementation.Remitta.Request.Remitta_RRR_Request;
import com.james.medpay.features.gateway.implementation.Remitta.Request.Remitta_Transaction_Request;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Component
public class RemittaGateway implements IGateway {

	public static final String REMITTA_DEMO_URL = "https://remitademo.net/remita/exapp/api/v1/send/api/echannelsvc/merchant/api/paymentinit";
	public String orderId;

	@Override
	public ResponseEntity<Map<String, String>> generatePaymentReference(GatewayRequest request) {
		this.orderId = RandomStringUtils.randomNumeric(14);
		Remitta_Transaction_Request transactionRequest = (Remitta_Transaction_Request) request;
		return this.sendHttpRequest(transactionRequest);
	}

	private ResponseEntity<Map<String, String>> sendHttpRequest(Remitta_Transaction_Request request) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(HttpHeaders.AUTHORIZATION, generateDemoAuthorizationHeader(request));

		String requestBody = this.generateDemoRRR_Request(request.toRemittaPaymentInfo(orderId));
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		ResponseEntity<String> response = restTemplate.exchange(REMITTA_DEMO_URL, HttpMethod.POST, requestEntity, String.class);
		return transformToMap(response);
	}

	private ResponseEntity<Map<String, String>> transformToMap(ResponseEntity<String> response){
		Map<String, String> map = new HashMap<>();
		if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null){
			String replaced = response.getBody().replaceFirst("jsonp \\(", "").replaceAll("\\)$", "");

			try {
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode jsonNode = objectMapper.readTree(replaced);

				// Iterate over the fields and extract keys and values
				jsonNode.fields().forEachRemaining(entry -> {
					String key = entry.getKey();
					String value = entry.getValue().asText();
					map.put(key, value);
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return new ResponseEntity<>(map, response.getStatusCode());
	}


	private String generateDemoAuthorizationHeader(Remitta_Transaction_Request request) {
		double amount = request.getTransactionRequest().getAmount().doubleValue();
		String hash = this.generateDemoHash(amount);
		String consumerKey = Remitta_Demo_RRR.consumerKey;
		return String.format("remitaConsumerKey=%s,remitaConsumerToken=%s", consumerKey, hash);
	}

	private String generateDemoHash(Double amount) {
		String contact = Remitta_Demo_RRR.merchantId + Remitta_Demo_RRR.serviceTypeId + orderId + amount + Remitta_Demo_RRR.apiKey;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			byte[] hashBytes = digest.digest(contact.getBytes(StandardCharsets.UTF_8));

			StringBuilder result = new StringBuilder();
			for (byte b : hashBytes) {
				result.append(String.format("%02x", b));
			}
			return result.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

	}

	private String generateDemoRRR_Request(Remitta_Payment_Info request) {

		Remitta_RRR_Request requestBody = new Remitta_RRR_Request();
		requestBody.setServiceTypeId(Remitta_Demo_RRR.serviceTypeId);
		requestBody.setAmount(request.getAmount());
		requestBody.setOrderId(request.getOrderId());
		requestBody.setPayerName(request.getPayerName());
		requestBody.setPayerEmail(request.getPayerEmail());
		requestBody.setPayerPhone(request.getPayerEmail());
		requestBody.setDescription(request.getPaymentDescription());

		// Serialize the request body object to JSON string
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(requestBody);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}

	}
}
