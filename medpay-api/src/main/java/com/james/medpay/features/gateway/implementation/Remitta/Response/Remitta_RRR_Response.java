/*
 * @Author: james.junior
 * @Date: 7/7/23 14:34
 *
 * @Project: medpay-api
 */

package com.james.medpay.features.gateway.implementation.Remitta.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.james.medpay.features.gateway.contract.GatewayResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Remitta_RRR_Response extends GatewayResponse {

	@JsonProperty("statuscode")
	private String statuscode;

	@JsonProperty("RRR")
	private String rrr;

	@JsonProperty("status")
	private String status;

	@JsonProperty("statusMessage")
	private String statusMessage;

	@JsonProperty("uniqueReference")
	private String uniqueReference;

	@JsonProperty("responseParams")
	private String responseParams;

}
