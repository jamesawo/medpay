package com.james.medpay.features.users.domain.repository.dataRepository;

import com.james.medpay.features.users.domain.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserDataRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
	Optional<UserEntity> findByEmailIgnoreCase( String email );

	List<UserEntity> findAll( Specification<UserEntity> specification );
}
