/*
 * @Author: james.junior
 * @Date: 7/5/23 15:59
 *
 * @Project: medpay-api
 */

package com.james.medpay.features.billing.data.usecase_impl;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.features.billing.data.repository.PatientBillItemRepository;
import com.james.medpay.features.billing.domain.entity.PatientBillItem;
import com.james.medpay.features.billing.domain.usecase.IPatientBillItemUsecase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class PatientBillItemUsecaseImpl implements IPatientBillItemUsecase {
	private final PatientBillItemRepository repository;

	@Override
	public PatientBillItem save(PatientBillItem item) {
		return this.repository.save(item);
	}
}
