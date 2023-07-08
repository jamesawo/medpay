/*
 * @Author: james.junior
 * @Date: 7/7/23 21:26
 *
 * @Project: medpay-api
 */

package com.james.medpay.features.gateway.implementation.Remitta.Response;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.james.medpay.features.gateway.contract.GatewayResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RemittaResponse extends GatewayResponse {

	@JsonRawValue
	private String jsonResponse;


	public RemittaResponse(String jsonString) {
		this.jsonResponse = jsonString;
	}
}
