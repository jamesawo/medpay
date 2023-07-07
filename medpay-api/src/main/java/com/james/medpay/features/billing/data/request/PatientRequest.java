/*
 * @Author: james.junior
 * @Date: 7/5/23 12:45
 *
 * @Project: medpay-api
 */

package com.james.medpay.features.billing.data.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.james.medpay.features.users.domain.entity.enums.GenderEnum;
import lombok.Data;


@Data
@JsonInclude( JsonInclude.Include.NON_NULL )
public class PatientRequest {
	private Long id;
	private String firstName;
	private String lastName;
	private String otherName;
	private String dateOfBirth;
	private String phone;
	private String address;
	private GenderEnum gender;
	private Integer age;
	private String registeredAt;
	private Integer patientNumber;
	private String patientNumberPrefix;
	private String uniqueCode;
}
