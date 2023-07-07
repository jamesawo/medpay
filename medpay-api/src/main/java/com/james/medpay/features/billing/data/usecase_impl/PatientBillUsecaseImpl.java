/*
 * @Author: james.junior
 * @Date: 7/5/23 15:41
 *
 * @Project: medpay-api
 */

package com.james.medpay.features.billing.data.usecase_impl;

import com.james.medpay.core.annotation.UseCase;
import com.james.medpay.features.billing.data.repository.PatientBillRepository;
import com.james.medpay.features.billing.data.specification.PatientBillSearchSpecification;
import com.james.medpay.features.billing.domain.entity.Patient;
import com.james.medpay.features.billing.domain.entity.PatientBill;
import com.james.medpay.features.billing.domain.enums.BillStatus;
import com.james.medpay.features.billing.domain.usecase.IPatientBillUsecase;
import com.james.medpay.features.billing.domain.usecase.IPatientUsecase;
import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.features.hospital.domain.usecase.basicDetail.HospitalGetHospitalCompleteDetailUsecase;
import com.james.medpay.features.users.domain.entity.UserEntity;
import com.james.medpay.features.users.domain.entity.contract.IUserEntity;
import com.james.medpay.features.users.domain.usecase.usersBaseUsecase.UserGetByIdUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static com.james.medpay.core.constant.AppDefaultConstant.DEFAULT_CODE_START;

@UseCase
@RequiredArgsConstructor
public class PatientBillUsecaseImpl implements IPatientBillUsecase {
	private static final String BILL_NUMBER_PREFIX = "INV";
	private final PatientBillRepository repository;
	private final UserGetByIdUsecase getByIdUsecase;
	private final IPatientUsecase patientUsecase;
	private final HospitalGetHospitalCompleteDetailUsecase getHospitalUsecase;


	@Override
	public PatientBill register(PatientBill bill) {
		this.setBillCreatedBy(bill);
		this.setBillPatient(bill);
		this.setBillNumber(bill);
		this.setHospital(bill);

		return this.repository.save(bill);
	}

	@Override
	public List<PatientBill> search(String term) {
		Specification<PatientBill> specification = PatientBillSearchSpecification.search(term);
		return this.repository.findAll(specification);
	}

	@Override
	public void updateBillStatus(String billNumber, BillStatus status) {
		this.repository.updatePatientBillStatusByBillNumber(status, billNumber);
	}


	private void setHospital(PatientBill bill){
		Optional<HospitalEntity> optional = this.getHospitalUsecase.execute(bill.getHospital().getId());
		optional.ifPresent(bill::setHospital);
	}

	private void setBillNumber(PatientBill bill){
		Integer billNumber = this.getBillNumber();
		bill.setBillAtomic(billNumber);
		bill.setBillNumber(BILL_NUMBER_PREFIX + billNumber);
	}

	private Integer getBillNumber() {
		Optional<PatientBill> optional = this.repository.findTopByOrderByIdDesc();
		return optional.map(bill -> new AtomicInteger(bill.getBillAtomic()).incrementAndGet()).orElse(DEFAULT_CODE_START);
	}

	private void setBillCreatedBy(PatientBill bill) {
		Optional<IUserEntity> optionalUser = this.getByIdUsecase.execute(bill.getCreatedBy().getId());
		optionalUser.ifPresent(user -> bill.setCreatedBy((UserEntity) user));
	}

	private void setBillPatient(PatientBill bill) {
		Optional<Patient> optionalPatient = this.patientUsecase.findOne(bill.getPatient().getId());
		optionalPatient.ifPresent(bill::setPatient);
	}


}
