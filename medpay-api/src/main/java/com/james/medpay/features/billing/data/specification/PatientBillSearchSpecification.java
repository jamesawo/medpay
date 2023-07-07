/*
 * @Author: james.junior
 * @Date: 7/5/23 16:36
 *
 * @Project: medpay-api
 */

package com.james.medpay.features.billing.data.specification;

import com.james.medpay.features.billing.domain.entity.PatientBill;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PatientBillSearchSpecification {

	private static Root<PatientBill> mainRoot;

	public static Specification<PatientBill> search(String term){

		return (root, criteriaQuery, criteriaBuilder) ->  {
			mainRoot = root;
			List<Predicate> predicates = new ArrayList<>();

			predicates.add(billNumberPredicate(criteriaBuilder, term));

			return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
		};

	}

	private static Predicate billNumberPredicate(CriteriaBuilder criteriaBuilder, String term){
		return criteriaBuilder.like( criteriaBuilder.lower(mainRoot.get("billNumber")), "%" + term.toLowerCase() + "%" );
	}
}
