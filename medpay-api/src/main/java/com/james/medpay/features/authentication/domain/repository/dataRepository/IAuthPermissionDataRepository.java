package com.james.medpay.features.authentication.domain.repository.dataRepository;

import com.james.medpay.features.authentication.domain.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthPermissionDataRepository extends JpaRepository<Permission, Long> {
}
