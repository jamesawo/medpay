/*
 * @Author: james.junior
 * @Date: 7/5/23 15:25
 *
 * @Project: medpay-api
 */

package com.james.medpay.features.billing.data.interactor;

import com.james.medpay.core.annotation.Interactor;
import com.james.medpay.features.billing.data.request.PatientBillItemRequest;
import com.james.medpay.features.billing.data.request.PatientBillRequest;
import com.james.medpay.features.billing.data.request.PatientRequest;
import com.james.medpay.features.billing.domain.entity.Patient;
import com.james.medpay.features.billing.domain.entity.PatientBill;
import com.james.medpay.features.billing.domain.entity.PatientBillItem;
import com.james.medpay.features.billing.domain.enums.BillStatus;
import com.james.medpay.features.billing.domain.usecase.IPatientBillItemUsecase;
import com.james.medpay.features.billing.domain.usecase.IPatientBillUsecase;
import com.james.medpay.features.billing.domain.usecase.IPatientUsecase;
import com.james.medpay.features.hospital.data.request.HospitalRequest;
import com.james.medpay.features.hospital.data.request.HospitalServiceRequest;
import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.features.hospital.domain.entity.HospitalService;
import com.james.medpay.features.users.data.request.UserRequest;
import com.james.medpay.features.users.domain.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@Interactor
@RequiredArgsConstructor
public class PatientBillInteractor {
	private final IPatientBillUsecase usecase;
	private final IPatientBillItemUsecase itemUsecase;

	public ResponseEntity<PatientBillRequest> register(PatientBillRequest request) {
		PatientBill model = this.toModel(request);
		PatientBill bill = this.usecase.register(model);

		this.saveBillItems(bill, request);

		request.setBillNumber(bill.getBillNumber());
		request.setId(bill.getId());

		return ok().body(request);
	}

	public ResponseEntity<List<PatientBillRequest>> search(String term) {
		List<PatientBill> searchResult = this.usecase.search(term);
		List<PatientBillRequest> requestList = searchResult.stream().map(this::toRequest).collect(Collectors.toList());
		return ok().body(requestList);
	}

	private void saveBillItems(PatientBill bill, PatientBillRequest billRequest) {
		if (billRequest.getItems() != null) {
			for (PatientBillItemRequest item : billRequest.getItems()) {

				PatientBillItem billItem = new PatientBillItem();
				billItem.setBill(bill);
				billItem.setAmount(item.getAmount());
				billItem.setService(HospitalService.builder().id(item.getService().getId()).build());

				this.itemUsecase.save(billItem);
			}
		}
	}

	private PatientBill toModel(PatientBillRequest request) {
		PatientBill bill = new PatientBill();
		bill.setBillAmount(request.getBillAmount());
		bill.setStatus(request.getStatus());
		bill.setPatient(Patient.builder().id(request.getPatient().getId()).build());
		bill.setCreatedBy(UserEntity.builder().id(request.getCreatedBy().getId()).build());
		bill.setCreatedAt(LocalDate.now());
		bill.setHospital(HospitalEntity.builder().id(request.getHospital().getId()).build());
		return bill;
	}

	private PatientBillRequest toRequest(PatientBill model) {
		PatientBillRequest request = new PatientBillRequest();
		request.setId(model.getId());
		request.setPatient(PatientInteractor.toRequest(model.getPatient()));
		request.setItems(billItemsToRequest(model.getItems()));
		request.setHospital(HospitalRequest.from(model.getHospital()));

		request.setBillNumber(model.getBillNumber());
		request.setCreatedAt(model.getCreatedAt().toString());
		request.setBillAmount(model.getBillAmount());
		request.setStatus(model.getStatus());
		request.setCreatedBy(UserRequest.trimToRequest(model.getCreatedBy()));

		return request;
	}

	private List<PatientBillItemRequest> billItemsToRequest(List<PatientBillItem> items){
		List<PatientBillItemRequest> billItemRequests = new ArrayList<>();
		if (items != null && !items.isEmpty()) {
			for (PatientBillItem item : items) {
				PatientBillItemRequest request = new PatientBillItemRequest();
				request.setId(item.getId());
				request.setAmount(item.getAmount());
				request.setService(HospitalServiceRequest.toRequest(item.getService()));
				billItemRequests.add(request);
			}
		}
		return billItemRequests;
	}
}
