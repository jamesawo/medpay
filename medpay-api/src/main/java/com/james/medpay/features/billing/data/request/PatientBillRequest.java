/*
 * @Author: james.junior
 * @Date: 7/5/23 12:45
 *
 * @Project: medpay-api
 */

package com.james.medpay.features.billing.data.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.james.medpay.features.billing.domain.entity.Patient;
import com.james.medpay.features.billing.domain.entity.PatientBillItem;
import com.james.medpay.features.billing.domain.enums.BillStatus;
import com.james.medpay.features.hospital.data.request.HospitalRequest;
import com.james.medpay.features.users.data.request.UserRequest;
import com.james.medpay.features.users.domain.entity.UserEntity;
import com.james.medpay.features.users.domain.entity.enums.GenderEnum;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Data
@JsonInclude( JsonInclude.Include.NON_NULL )
public class PatientBillRequest {
	private Long id;
	private PatientRequest patient;
	private List<PatientBillItemRequest> items;
	private HospitalRequest hospital;

	private String billNumber;
	private String createdAt;
	private Double billAmount;
	private BillStatus status;
	private UserRequest createdBy;
}
