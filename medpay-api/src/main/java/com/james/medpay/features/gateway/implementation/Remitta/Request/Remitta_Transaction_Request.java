/*
 * @Author: james.junior
 * @Date: 7/7/23 13:45
 *
 * @Project: medpay-api
 */

package com.james.medpay.features.gateway.implementation.Remitta.Request;

import com.james.medpay.features.gateway.contract.GatewayRequest;
import com.james.medpay.features.transaction.data.request.TransactionPayerDetailRequest;
import com.james.medpay.features.transaction.data.request.TransactionRequest;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Remitta_Transaction_Request extends GatewayRequest{
	private TransactionRequest transactionRequest;

	public Remitta_Transaction_Request(TransactionRequest transactionRequest) {
		this.transactionRequest = transactionRequest;
	}


	public Remitta_Payment_Info toRemittaPaymentInfo() {
		TransactionPayerDetailRequest payerDetail = transactionRequest.getPayerDetail();

		Remitta_Payment_Info remitta_payment_info_ = new Remitta_Payment_Info();
		remitta_payment_info_.setAmount(transactionRequest.getAmount().doubleValue());
		remitta_payment_info_.setOrderId(Remitta_Demo_RRR.orderId);
		remitta_payment_info_.setPayerName(payerDetail.getFullName());
		remitta_payment_info_.setPayerEmail("test@example.com");
		remitta_payment_info_.setPayerPhone(payerDetail.getPhoneNumber());
		remitta_payment_info_.setPaymentDescription("Hospital service demo payment ");

		return remitta_payment_info_;
	}
}
