package com.james.medpay.features.hospital.data.request.specification;

import com.james.medpay.features.hospital.data.request.searchRequest.HospitalSearchRequest;
import com.james.medpay.features.hospital.domain.entity.HospitalApiConfiguration;
import com.james.medpay.features.hospital.domain.entity.HospitalDetail;
import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class HospitalSearchSpecification {

	public static Specification<HospitalEntity> byName(final String searchTerm){
		return ( root, criteriaQuery, criteriaBuilder ) -> {
			List<Predicate> predicates = new ArrayList<>();

			Join<HospitalEntity, HospitalDetail> join = root.join( "detail" );
			predicates.add( criteriaBuilder.like(
					criteriaBuilder.lower( join.get( "name" ) ),
					"%" + searchTerm.toLowerCase() + "%"
			) );
			return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
		};
	}

	public static Specification<HospitalEntity> byRequestPayload( HospitalSearchRequest request) {
		return ( root, query, criteriaBuilder ) -> {
			List<Predicate> predicates = new ArrayList<>();

			if ( ObjectUtils.isNotEmpty( request.getHospitalName() ) ) {
				Join<HospitalEntity, HospitalDetail> join = root.join( "detail" );
				predicates.add( criteriaBuilder.like(
						criteriaBuilder.lower( join.get( "name" ) ),
						"%" + request.getHospitalName().toLowerCase() + "%"
				) );
			}

			if ( ObjectUtils.isNotEmpty( request.getSupportName() ) ) {
				Join<HospitalEntity, HospitalDetail> join = root.join( "detail" );
				predicates.add( criteriaBuilder.like(
						criteriaBuilder.lower( join.get( "supportName" ) ),
						"%" + request.getSupportName().toLowerCase() + "%"
				) );
			}

			if ( ObjectUtils.isNotEmpty( request.getChannelEnum() ) ) {
				Join<HospitalEntity, HospitalDetail> join = root.join( "detail" );
				predicates.add( criteriaBuilder.equal( join.get( "supportChannel" ), request.getChannelEnum() ) );
			}

			if ( ObjectUtils.isNotEmpty( request.getDateRange() ) ) {
				if ( ObjectUtils.isNotEmpty( request.getDateRange().getEndDate() ) ) {
					predicates.add( criteriaBuilder.lessThanOrEqualTo( root.get( "startDate" ), request.getDateRange().getEndDate() ) );
				}
				if ( ObjectUtils.isNotEmpty( request.getDateRange().getStartDate() ) ) {
					predicates.add( criteriaBuilder.greaterThanOrEqualTo( root.get( "startDate" ), request.getDateRange().getStartDate() ) );
				}
			}

			if (ObjectUtils.isNotEmpty( request.getIsEnabled())){
				if (request.getIsEnabled()) predicates.add(criteriaBuilder.isTrue(root.get("isEnabled").as(Boolean.class)));
				if (!request.getIsEnabled()) predicates.add(criteriaBuilder.isFalse(root.get("isEnabled").as(Boolean.class)));
			}

			if (ObjectUtils.isNotEmpty( request.getHasHospitalSoftware() )){
				if (request.getHasHospitalSoftware()) {
					predicates.add(criteriaBuilder.isTrue(root.get("hasHospitalSoftware").as(Boolean.class)));
				}
				if (!request.getHasHospitalSoftware()){
					predicates.add(criteriaBuilder.isFalse(root.get("hasHospitalSoftware").as(Boolean.class)));
				}
			}

			if ( ObjectUtils.isNotEmpty( request.getChannelEnum() ) ) {
				Join<HospitalEntity, HospitalDetail> join = root.join( "detail" );
				predicates.add( criteriaBuilder.equal( join.get( "supportChannel" ), request.getChannelEnum() ) );
			}

			if (ObjectUtils.isNotEmpty( request.getEnvironmentEnum() )){
				Join<HospitalEntity, HospitalApiConfiguration> join = root.join( "apiConfiguration" );
				predicates.add( criteriaBuilder.equal( join.get( "environmentEnum" ), request.getEnvironmentEnum() ) );
			}

			if (ObjectUtils.isNotEmpty( request.getCollectionModelEnum() )){
				predicates.add(criteriaBuilder.equal(root.get("collectionModelEnum"), request.getCollectionModelEnum()));
			}

			query.orderBy(criteriaBuilder.desc(root.get("startDate")));
			return criteriaBuilder.and( predicates.toArray( new Predicate[ 0 ] ) );
		};
	}

}