/*
 * @Author: james.junior
 * @Date: 7/7/23 12:52
 *
 * @Project: medpay-api
 */

package com.james.medpay.features.gateway.implementation.Remitta;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.james.medpay.features.gateway.contract.IGateway;
import com.james.medpay.features.gateway.contract.GatewayRequest;
import com.james.medpay.features.gateway.contract.GatewayResponse;
import com.james.medpay.features.gateway.implementation.Remitta.Request.Remitta_Demo_RRR;
import com.james.medpay.features.gateway.implementation.Remitta.Request.Remitta_Payment_Info;
import com.james.medpay.features.gateway.implementation.Remitta.Request.Remitta_RRR_Request;
import com.james.medpay.features.gateway.implementation.Remitta.Request.Remitta_Transaction_Request;
import com.james.medpay.features.gateway.implementation.Remitta.Response.Remitta_RRR_Response;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class RemittaGateway implements IGateway {

	public static final String REMITTA_DEMO_URL = "https://remitademo.net/remita/exapp/api/v1/send/api/echannelsvc/merchant/api/paymentinit";


	@Override
	public GatewayResponse generatePaymentReference(GatewayRequest request) {
		Remitta_Transaction_Request remitta_transaction_request = (Remitta_Transaction_Request) request;
		return this.sendHttpRequest(remitta_transaction_request);
	}

	private Remitta_RRR_Response sendHttpRequest(Remitta_Transaction_Request request){
		// Create a RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();

		// Set the request headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(HttpHeaders.AUTHORIZATION, generateDemoAuthorizationHeader(request));

		String requestBody = this.generateDemoRRR_Request(request.toRemittaPaymentInfo());

		// Set the request entity
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

		// Make the POST request
		ResponseEntity<String> response = restTemplate.exchange(REMITTA_DEMO_URL, HttpMethod.POST, requestEntity, String.class);
		// Check the response status code
		if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
			try{
				ObjectMapper objectMapper = new ObjectMapper();
				String jsonResponse = response.getBody().replaceFirst("jsonp \\(", "").replaceAll("\\)$", "");

				return objectMapper.readValue(jsonResponse, Remitta_RRR_Response.class);


			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}

		} else {
			// Request failed
			System.out.println("Request failed with status code: " + response.getStatusCodeValue());
			return new Remitta_RRR_Response();
		}
	}

	private String generateDemoAuthorizationHeader(Remitta_Transaction_Request request){
		double amount = request.getTransactionRequest().getAmount().doubleValue();
		String hash = this.generateDemoHash(amount);
		String consumerKey = Remitta_Demo_RRR.consumerKey;
		return String.format("remitaConsumerKey=%s,remitaConsumerToken=%s", consumerKey, hash);
	}

	private String generateDemoHash(Double amount){
		String contact = Remitta_Demo_RRR.merchantId + Remitta_Demo_RRR.serviceTypeId + Remitta_Demo_RRR.orderId + amount + Remitta_Demo_RRR.apiKey;
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
