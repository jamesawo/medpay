/*
 * @Author: james.junior
 * @Date: 7/5/23 16:46
 *
 * @Project: medpay-api
 */

package com.james.medpay.features.billing.data.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.james.medpay.features.hospital.data.request.HospitalServiceRequest;
import lombok.Data;


@Data
@JsonInclude( JsonInclude.Include.NON_NULL )
public class PatientBillItemRequest {
	private Long id;
	private HospitalServiceRequest service;
	private Double amount;
}
