package com.james.medpay.features.hospital.data.request.specification;

import com.james.medpay.features.hospital.data.request.searchRequest.HospitalServiceGroupSearchRequest;
import com.james.medpay.features.hospital.domain.entity.HospitalServiceGroup;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;


import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Component
public class HospitalServiceGroupSearchSpecification {

	public static Specification< HospitalServiceGroup > fromSearchRequest(HospitalServiceGroupSearchRequest request) {
		return (root, query, criteriaBuilder) -> {
			List< Predicate > predicates = new ArrayList<>();

			if( isNotEmpty(request.getTitle()) ) {if( isNotEmpty(request.getTitle()) ) {
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + request.getTitle().toLowerCase() + "%"));
			}
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + request.getTitle().toLowerCase() + "%"));
			}

			if( isNotEmpty(request.getIsEnabled()) ) {
				if( request.getIsEnabled() ) {
					predicates.add(criteriaBuilder.isTrue(root.get("isEnabled").as(Boolean.class)));
				}
				if( ! request.getIsEnabled() ) {
					predicates.add(criteriaBuilder.isFalse(root.get("isEnabled").as(Boolean.class)));
				}
			}

			query.orderBy(criteriaBuilder.asc(root.get("title")));
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
