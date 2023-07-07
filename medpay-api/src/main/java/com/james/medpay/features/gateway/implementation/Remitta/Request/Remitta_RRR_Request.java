/*
 * @Author: james.junior
 * @Date: 7/7/23 13:21
 *
 * @Project: medpay-api
 */

package com.james.medpay.features.gateway.implementation.Remitta.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Remitta_RRR_Request  {
	private String serviceTypeId;
	private Double amount;
	private String orderId;
	private String payerName;
	private String payerEmail;
	private String payerPhone;
	private String description;
}
