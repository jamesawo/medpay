package com.james.medpay.features.hospital.domain.repository.contract;

import com.james.medpay.features.hospital.domain.entity.HospitalApiConfiguration;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalEnvironmentEnum;
import com.james.medpay.core.params.Param;

import java.util.Optional;

public interface IHospitalApiConfigRepository {
	// apiConfiguration
	Optional<HospitalApiConfiguration> getHospitalApiConfiguration( Long hospitalId );

	Optional<HospitalApiConfiguration> updateHospitalApiConfiguration( Param<HospitalApiConfiguration> param );

	Optional<Boolean> updateHospitalApiActiveEnvironment( Param<HospitalEnvironmentEnum> param );

}
