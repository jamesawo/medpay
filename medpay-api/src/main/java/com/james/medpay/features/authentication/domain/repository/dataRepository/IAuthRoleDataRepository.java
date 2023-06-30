package com.james.medpay.features.authentication.domain.repository.dataRepository;

import com.james.medpay.features.authentication.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAuthRoleDataRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName( String name );
	List<Role> findByNameIsContainingIgnoreCase( String name );
}
