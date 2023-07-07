/*
 * @Author: james.junior
 * @Date: 7/5/23 15:38
 *
 * @Project: medpay-api
 */

package com.james.medpay.features.billing.domain.usecase;

import com.james.medpay.features.billing.domain.entity.PatientBillItem;

public interface IPatientBillItemUsecase {
	PatientBillItem save(PatientBillItem item);
}
