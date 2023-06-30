package com.james.medpay.features.hospital.data.interactor.implementation;

import com.james.medpay.core.annotation.Interactor;
import com.james.medpay.features.hospital.data.interactor.contract.serviceGroup.IHospitalServiceGroupInteractor;
import com.james.medpay.features.hospital.data.request.HospitalServiceGroupRequest;
import com.james.medpay.features.hospital.domain.entity.HospitalServiceGroup;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.hospital.domain.usecase.serviceGroup.HospitalGetServiceGroupByHospitalUsecase;
import com.james.medpay.features.hospital.domain.usecase.serviceGroup.HospitalSearchServiceGroupByCriteriaUsecase;
import com.james.medpay.features.hospital.domain.usecase.serviceGroup.HospitalUpdateServiceGroupUsecase;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static com.james.medpay.features.hospital.data.request.HospitalServiceGroupRequest.toEntity;
import static org.springframework.http.ResponseEntity.ok;

@Interactor
@RequiredArgsConstructor
public class HospitalServiceGroupInteractor implements IHospitalServiceGroupInteractor {
	private final HospitalUpdateServiceGroupUsecase usecase;
	private final HospitalSearchServiceGroupByCriteriaUsecase byCriteriaUsecase;
	private final HospitalGetServiceGroupByHospitalUsecase byHospitalUsecase;

	@Override
	public ResponseEntity<Optional<HospitalServiceGroupRequest>> updateHospitalServiceGroup( HospitalServiceGroupRequest request ) {
		Optional<HospitalServiceGroup> optional = this.usecase.execute( new Param<>( request.getHospital().getId(), toEntity( request ) ) );
		return ok().body( optional.map( HospitalServiceGroupRequest::toRequest ) );
	}

	@Override
	public ResponseEntity< List<HospitalServiceGroupRequest> > getServiceGroupByHospitalId(Long hospitalId) {
		List< HospitalServiceGroup > list = this.byHospitalUsecase.execute(hospitalId);
		return _mapEntityListToRequestList(list);
	}

	private ResponseEntity< List< HospitalServiceGroupRequest > > _mapEntityListToRequestList(List< HospitalServiceGroup > list) {
		List< HospitalServiceGroupRequest > collect = list.stream().map(HospitalServiceGroupRequest::toRequest).collect(Collectors.toList());
		return ok(collect);
	}

}
