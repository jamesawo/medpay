/*
 * @Author: james.junior
 * @Date: 7/7/23 13:37
 *
 * @Project: medpay-api
 */

package com.james.medpay.features.gateway.implementation.Remitta.Request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Remitta_Payment_Info {
	private Double amount;
	private String orderId;
	private String payerName;
	private String payerEmail;
	private String payerPhone;
	private String paymentDescription;

}
