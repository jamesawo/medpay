
package com.james.medpay.features.users.data.request;

import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.features.users.domain.entity.UserBasicDetailsEntity;
import com.james.medpay.features.users.domain.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Component
public class UserSearchSpecification {
	private static Root<UserEntity> mainRoot;

	public static Specification<UserEntity> byCriteria( final String searchTerm ) {
		return ( root, criteriaQuery, criteriaBuilder ) -> {
			mainRoot = root;
			List<Predicate> predicates = new ArrayList<>();
			addEmailOrNickNameOrPhoneOrOtherNamesToPredicateList(predicates, searchTerm, criteriaBuilder);
			return criteriaBuilder.and( predicates.toArray( new Predicate[] { } ) );
		};
	}

	public static Specification<UserEntity> byCriteria( UserSearchRequest request ) {
		return ( root, query, criteriaBuilder ) -> {
			mainRoot = root;
			List<Predicate> predicates = new ArrayList<>();

			if ( isNotEmpty(request.getName()) ) {
				String name = request.getName();
				Predicate nNamePredicate = nickNamePredicate(name, criteriaBuilder);
				Predicate fNamePredicate = firstNamePredicate(name, criteriaBuilder);
				Predicate lNamePredicate = lastNamePredicate(name, criteriaBuilder);
				Predicate oNamePredicate = otherNamePredicate(name, criteriaBuilder);
				predicates.add( criteriaBuilder.or( nNamePredicate, fNamePredicate, lNamePredicate, oNamePredicate ) );
			}

			if ( isNotEmpty( request.getPhoneNumber() ) ) {
				Predicate phonePredicate = phonePredicate(request.getPhoneNumber(), criteriaBuilder);
				predicates.add( phonePredicate);
			}

			if ( isNotEmpty( request.getEmail() ) ) {
				Predicate emailPredicate = emailPredicate(request.getEmail(), criteriaBuilder);
				predicates.add( emailPredicate);
			}

			if ( isNotEmpty( request.getIsEnabled() ) ) {
				if ( request.getIsEnabled() ) predicates.add( criteriaBuilder.isTrue( root.get( "isEnabled" ).as( Boolean.class ) ) );
				if ( !request.getIsEnabled() ) predicates.add( criteriaBuilder.isFalse( root.get( "isEnabled" ).as( Boolean.class ) ) );
			}

			if ( isNotEmpty( request.getDateRange() ) ) {
				if ( isNotEmpty( request.getDateRange().getEndDate() ) ) {
					predicates.add( criteriaBuilder.lessThanOrEqualTo( root.get( "createdAt" ), request.getDateRange().getEndDate().atStartOfDay() ) );
				}
				if ( isNotEmpty( request.getDateRange().getStartDate() ) ) {
					predicates.add( criteriaBuilder.greaterThanOrEqualTo( root.get( "createdAt" ), request.getDateRange().getStartDate().atStartOfDay() ) );
				}
			}

			if ( isNotEmpty( request.getUserTypeEnum() ) ) {
				predicates.add( criteriaBuilder.equal( root.get( "userTypeEnum" ), request.getUserTypeEnum() ) );
			}

			if ( isNotEmpty( request.getGenderEnum() ) ) {
				Join<UserEntity, UserBasicDetailsEntity> basicDetailJoin = root.join( "basicDetails" );
				predicates.add( criteriaBuilder.equal( basicDetailJoin.get( "gender" ), request.getGenderEnum() ) );
			}

			if ( isNotEmpty( request.getHospital() ) && isNotEmpty( request.getHospital().getId() ) && request.getHospital().getId() != 0){
				Join<UserEntity, HospitalEntity> hospitalJoin = root.join( "hospital");
				predicates.add(criteriaBuilder.equal(hospitalJoin.get("id"), request.getHospital().getId() ));
			}

			//query.orderBy(criteriaBuilder.asc(root.get("nickName")));
			return criteriaBuilder.and( predicates.toArray( new Predicate[ 0 ] ) );
		};
	}

	private static Join<UserEntity, UserBasicDetailsEntity> basicDetailJoin (Root<UserEntity> root){
		return root.join( "basicDetails" );
	}

	private static Predicate phonePredicate(String searchTerm, CriteriaBuilder criteriaBuilder) {
		Join< UserEntity, UserBasicDetailsEntity > basicDetailJoin = basicDetailJoin(mainRoot);
		return criteriaBuilder.like(criteriaBuilder.lower(basicDetailJoin.get("phoneNumber")), "%" + searchTerm.toLowerCase() + "%");
	}

	private static Predicate firstNamePredicate(String searchTerm, CriteriaBuilder criteriaBuilder) {
		Join< UserEntity, UserBasicDetailsEntity > basicDetailJoin = basicDetailJoin(mainRoot);
		return criteriaBuilder.like( criteriaBuilder.lower( basicDetailJoin.get( "firstName" ) ), "%" + searchTerm.toLowerCase() + "%" );
	}

	private static Predicate lastNamePredicate(String searchTerm, CriteriaBuilder criteriaBuilder) {
		Join< UserEntity, UserBasicDetailsEntity > basicDetailJoin = basicDetailJoin(mainRoot);
		return criteriaBuilder.like( criteriaBuilder.lower( basicDetailJoin.get( "lastName" ) ), "%" + searchTerm.toLowerCase() + "%" );
	}

	private static Predicate otherNamePredicate(String searchTerm, CriteriaBuilder criteriaBuilder) {
		Join< UserEntity, UserBasicDetailsEntity > basicDetailJoin = basicDetailJoin(mainRoot);
		return criteriaBuilder.like( criteriaBuilder.lower( basicDetailJoin.get( "otherName" ) ), "%" + searchTerm.toLowerCase() + "%" );
	}

	private static Predicate nickNamePredicate(String searchTerm, CriteriaBuilder criteriaBuilder) {
		return criteriaBuilder.like(criteriaBuilder.lower(mainRoot.get("nickName")), "%" + searchTerm.toLowerCase() + "%");
	}

	private static Predicate emailPredicate(String searchTerm, CriteriaBuilder criteriaBuilder) {
		return criteriaBuilder.like( criteriaBuilder.lower( mainRoot.get( "email" ) ), "%" + searchTerm.toLowerCase() + "%" );
	}

	private static void addEmailOrNickNameOrPhoneOrOtherNamesToPredicateList(
			List<Predicate> predicates, String searchTerm,
			CriteriaBuilder criteriaBuilder) {

		Predicate emailPredicate = emailPredicate(searchTerm, criteriaBuilder);
		Predicate nNamePredicate = nickNamePredicate(searchTerm, criteriaBuilder);
		Predicate phonePredicate = phonePredicate(searchTerm, criteriaBuilder);
		Predicate fNamePredicate = firstNamePredicate(searchTerm, criteriaBuilder);
		Predicate lNamePredicate = lastNamePredicate(searchTerm, criteriaBuilder);
		Predicate oNamePredicate = otherNamePredicate(searchTerm, criteriaBuilder);
		predicates.add( criteriaBuilder.or( emailPredicate, nNamePredicate, phonePredicate, fNamePredicate, lNamePredicate, oNamePredicate ) );
	}

}
