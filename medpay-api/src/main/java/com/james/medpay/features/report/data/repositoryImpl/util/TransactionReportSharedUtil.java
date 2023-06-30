
package com.james.medpay.features.report.data.repositoryImpl.util;

import com.james.medpay.core.exception.InvalidRequestException;
import com.james.medpay.features.hospital.domain.entity.HospitalDetail;
import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.features.hospital.domain.usecase.basicDetail.HospitalGetHospitalCompleteDetailUsecase;
import com.james.medpay.features.report.data.request.TransactionReportRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Service
@RequiredArgsConstructor
public class TransactionReportSharedUtil {

	public static final String NO_REPORT_FORMAT = "A report format is required";
	public static final String NO_DATE_RANGE = "A valid date range is required";
	public static final String NO_STATUS = "Transaction status is required";
	public static final String EMPTY = "";
	public static final String HOSPITAL_404 = "HOSPITAL NOT FOUND";
	public static final String NO_HOSPITAL = "Hospital is required";

	private final HospitalGetHospitalCompleteDetailUsecase getHospitalCompleteDetailUsecase;


	public void checkIsValidOrThrow( TransactionReportRequest request ) {
		if(isEmpty( request.getHospital() )){
			throw new InvalidRequestException( NO_HOSPITAL );
		}

		if ( isEmpty( request.getDateRange() ) || isEmpty( request.getDateRange().getStartDate() ) || isEmpty( request.getDateRange().getEndDate() ) ){
			throw new InvalidRequestException( NO_DATE_RANGE );
		}

		if ( isEmpty( request.getFormat() ) ){
			throw new InvalidRequestException( NO_REPORT_FORMAT );
		}

		if( isEmpty( request.getStatus() ) ){
			throw new InvalidRequestException( NO_STATUS );
		}
	}

	public  HashMap<String, Object> getReportMapData( TransactionReportRequest reportRequest, String reportTitle, Double totalSumAmount ) {
		Optional<HospitalEntity> optional = this.getHospitalCompleteDetailUsecase.execute( reportRequest.getHospital().getId() );
		if ( optional.isPresent() ) {
			HospitalDetail detail = optional.get().getDetail();
			HashMap<String, Object> map = new HashMap<>();
			map.put( "hospitalName", detail.getName().toUpperCase() );
			map.put( "reportTitle", reportTitle );
			map.put( "hospitalAddress", isNotEmpty( detail.getHospitalAddress() ) ? detail.getHospitalAddress().toUpperCase() : EMPTY );
			map.put( "hospitalLogo", detail.getHospitalLogoUrl() );
			map.put( "sumTotalAmount", totalSumAmount );
			return map;
		}
		throw new InvalidRequestException( HOSPITAL_404 );
	}
}
