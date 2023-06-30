package com.james.medpay.features.users.domain.repository.dataRepository;

import com.james.medpay.features.users.domain.entity.UserConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserConfigurationDataRepository extends JpaRepository<UserConfigurationEntity, Long> {
}
