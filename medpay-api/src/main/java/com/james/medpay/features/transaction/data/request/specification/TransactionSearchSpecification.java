
package com.james.medpay.features.transaction.data.request.specification;

import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.features.transaction.data.request.searchRequest.TransactionSearchRequest;
import com.james.medpay.features.transaction.domain.entity.TransactionEntity;
import com.james.medpay.features.transaction.domain.entity.TransactionPayerDetail;
import com.james.medpay.features.transaction.domain.entity.TransactionPaymentDetail;
import com.james.medpay.features.transaction.domain.entity.enums.TransactionStatusEnum;
import com.james.medpay.features.users.domain.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Component
public class TransactionSearchSpecification {

	private static Root<TransactionEntity> mainRoot;

	public static Specification<TransactionEntity> byCriteria(TransactionSearchRequest request) {
		return (root, criteriaQuery, criteriaBuilder) -> {
			mainRoot = root;
			List<Predicate> predicates = new ArrayList<>();

			if (isNotEmpty(request.getHospital()) && isNotEmpty(request.getHospital().getId())) {
				predicates.add(hospitalPredicate(request.getHospital().getId(), criteriaBuilder));
			}

			if (isNotEmpty(request.getUser()) && isNotEmpty(request.getUser().getId())) {
				predicates.add(userPredicate(request.getUser().getId(), criteriaBuilder));
			}

			if (isNotEmpty(request.getReference()) && !request.getReference().isEmpty()) {
				predicates.add(referencePredicate(request.getReference(), criteriaBuilder));
			}

			if (isNotEmpty(request.getSerial()) && !request.getSerial().isEmpty()) {
				predicates.add(serialPredicate(request.getSerial(), criteriaBuilder));
			}

			if (isNotEmpty(request.getBillNumber()) && !request.getBillNumber().isEmpty()) {
				predicates.add(billNumberPredicate(request.getBillNumber(), criteriaBuilder));
			}

			if (isNotEmpty(request.getStatus())) {
				predicates.add(statusPredicate(request.getStatus(), criteriaBuilder));
			}

			if (isNotEmpty(request.getDateRange())) {
				if (isNotEmpty(request.getDateRange().getEndDate())) {
					predicates.add(
							criteriaBuilder.lessThanOrEqualTo(root.get("date"), request.getDateRange().getEndDate()));
				}
				if (isNotEmpty(request.getDateRange().getStartDate())) {
					predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"),
							request.getDateRange().getStartDate()));
				}
			}

			if (isNotEmpty(request.getPayer()) && !request.getPayer().isEmpty()) {
				predicates.add(payerPredicate(request.getPayer(), criteriaBuilder));
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[] {}));
		};
	}

	private static Predicate hospitalPredicate(Long hospitalId, CriteriaBuilder criteriaBuilder) {
		Join<TransactionEntity, HospitalEntity> join = mainRoot.join("hospital");
		return criteriaBuilder.equal(join.get("id"), hospitalId);
	}

	private static Predicate userPredicate(Long userId, CriteriaBuilder criteriaBuilder) {
		Join<TransactionEntity, UserEntity> join = mainRoot.join("user");
		return criteriaBuilder.equal(join.get("id"), userId);
	}

	private static Predicate referencePredicate(String reference, CriteriaBuilder criteriaBuilder) {
		return criteriaBuilder.like(criteriaBuilder.lower(mainRoot.get("reference")),
				"%" + reference.toLowerCase() + "%");
	}

	private static Predicate serialPredicate(String token, CriteriaBuilder criteriaBuilder) {
		return criteriaBuilder.like(criteriaBuilder.lower(mainRoot.get("token")), "%" + token.toLowerCase() + "%");
	}

	private static Predicate billNumberPredicate(String billNumber, CriteriaBuilder criteriaBuilder) {
		Join<TransactionEntity, TransactionPaymentDetail> join = mainRoot.join("paymentDetail");
		return criteriaBuilder.like(criteriaBuilder.lower(join.get("billNumber")),
				"%" + billNumber.toLowerCase() + "%");
	}

	private static Predicate statusPredicate(TransactionStatusEnum status, CriteriaBuilder criteriaBuilder) {
		return criteriaBuilder.equal(mainRoot.get("statusEnum"), status);
	}

	private static Predicate payerPredicate(String searchTerm, CriteriaBuilder criteriaBuilder) {
		Predicate fullNamePredicate = payerFullNamePredicate(searchTerm, criteriaBuilder);
		Predicate payerPhonePredicate = payerPhonePredicate(searchTerm, criteriaBuilder);
		Predicate payerPatientNumber = payerPatientNumberPredicate(searchTerm, criteriaBuilder);
		return criteriaBuilder.or(fullNamePredicate, payerPhonePredicate, payerPatientNumber);
	}

	private static Predicate payerFullNamePredicate(String searchTerm, CriteriaBuilder criteriaBuilder) {
		Join<TransactionEntity, TransactionPayerDetail> payerJoin = payerJoin(mainRoot);
		return criteriaBuilder.like(criteriaBuilder.lower(payerJoin.get("fullName")),
				"%" + searchTerm.toLowerCase() + "%");
	}

	private static Predicate payerPhonePredicate(String searchTerm, CriteriaBuilder criteriaBuilder) {
		Join<TransactionEntity, TransactionPayerDetail> payerJoin = payerJoin(mainRoot);
		return criteriaBuilder.like(criteriaBuilder.lower(payerJoin.get("phoneNumber")),
				"%" + searchTerm.toLowerCase() + "%");
	}

	private static Predicate payerPatientNumberPredicate(String searchTerm, CriteriaBuilder criteriaBuilder) {
		Join<TransactionEntity, TransactionPayerDetail> payerJoin = payerJoin(mainRoot);
		return criteriaBuilder.like(criteriaBuilder.lower(payerJoin.get("patientNumber")),
				"%" + searchTerm.toLowerCase() + "%");
	}

	private static Join<TransactionEntity, TransactionPayerDetail> payerJoin(Root<TransactionEntity> root) {
		return root.join("payerDetail");
	}

}
