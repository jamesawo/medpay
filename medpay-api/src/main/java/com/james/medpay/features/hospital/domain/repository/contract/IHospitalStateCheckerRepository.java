package com.james.medpay.features.hospital.domain.repository.contract;

import java.util.Map;
import java.util.Optional;

public interface IHospitalStateCheckerRepository {

	Optional<Boolean> isHospitalConfigured( Long hospitalId );

	Optional<Map<String, Object>> runHospitalStateChecker( Long hospitalId );
}
