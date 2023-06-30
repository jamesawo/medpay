package com.james.medpay.features.users.domain.repository.dataRepository;

import com.james.medpay.features.users.domain.entity.UserBasicDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserBasicDetailsDataRepository extends JpaRepository<UserBasicDetailsEntity, Long> {
}
