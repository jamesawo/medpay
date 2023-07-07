/*
 * @Author: james.junior
 * @Date: 7/5/23 14:25
 *
 * @Project: medpay-api
 */

package com.james.medpay.features.billing.data.specification;

import com.james.medpay.features.billing.domain.entity.Patient;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PatientSearchSpecification {

	private static Root<Patient> mainRoot;

	public static Specification<Patient> search(String term){
		return (root, criteriaQuery, criteriaBuilder) -> {
			mainRoot = root;
			List<Predicate> predicates = new ArrayList<>();
			if (term != null){
				predicates.add(patientNameOrPhoneOrCodePredicate(criteriaBuilder, term));
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[] {}));
		};
	}

	private static Predicate patientNameOrPhoneOrCodePredicate(CriteriaBuilder criteriaBuilder, String term){
		Predicate firstName = genericPredicate(criteriaBuilder, term, "firstName");
		Predicate lastName = genericPredicate(criteriaBuilder, term, "lastName");
		Predicate otherName = genericPredicate(criteriaBuilder, term, "otherName");
		Predicate phone = genericPredicate(criteriaBuilder, term, "phone");
		Predicate uniqueCode = genericPredicate(criteriaBuilder, term, "uniqueCode");

		return criteriaBuilder.or(firstName, lastName, otherName, phone, uniqueCode);
	}

	private static Predicate genericPredicate(CriteriaBuilder criteriaBuilder, String term, String column){
		return criteriaBuilder.like( criteriaBuilder.lower(mainRoot.get(column)), "%" + term.toLowerCase() + "%");
	}


}
