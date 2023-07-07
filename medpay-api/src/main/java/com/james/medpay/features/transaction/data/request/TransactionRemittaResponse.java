/*
 * @Author: james.junior
 * @Date: 7/7/23 12:37
 *
 * @Project: medpay-api
 */

package com.james.medpay.features.transaction.data.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude( JsonInclude.Include.NON_NULL )
public class TransactionRemittaResponse {
	private String statuscode;
	private String status;
	private String RRR;
}
