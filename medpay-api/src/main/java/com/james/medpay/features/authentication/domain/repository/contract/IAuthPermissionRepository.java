package com.james.medpay.features.authentication.domain.repository.contract;

import com.james.medpay.features.authentication.domain.entity.contract.IPermission;

import java.util.Optional;
import java.util.Set;

public interface IAuthPermissionRepository {

	Set<IPermission> findAll();

	Optional<Boolean> createMany( Set<IPermission> permissions );

}
