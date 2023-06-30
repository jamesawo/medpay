
package com.james.medpay.features.report.data.repositoryImpl;

import com.james.medpay.core.annotation.RepositoryImpl;
import com.james.medpay.core.exception.InvalidRequestException;
import com.james.medpay.core.params.BiParam;
import com.james.medpay.features.report.data.repositoryImpl.util.TransactionReportSharedUtil;
import com.james.medpay.features.report.data.repositoryImpl.util.TransactionReportWithRevenueHeadUtil;
import com.james.medpay.features.report.data.repositoryImpl.util.TransactionReportWithoutRevenueHeadUtil;
import com.james.medpay.features.report.data.request.TransactionReportRequest;
import com.james.medpay.features.report.domain.repository.transaction.IReportForTransactionByHospitalRepository;
import com.james.medpay.features.transaction.data.request.searchRequest.TransactionSearchRequest;
import com.james.medpay.features.transaction.data.request.specification.TransactionSearchSpecification;
import com.james.medpay.features.transaction.domain.entity.TransactionEntity;
import com.james.medpay.features.transaction.domain.repository.contract.ITransactionEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@RepositoryImpl
@RequiredArgsConstructor
public class ReportForTransactionByHospitalRepositoryImpl implements IReportForTransactionByHospitalRepository {

	public static final String NO_REVENUE_HEAD = "Revenue Head is required";

	private final TransactionReportWithRevenueHeadUtil withRevenueHeadUtil;
	private final TransactionReportWithoutRevenueHeadUtil withoutRevenueHeadUtil;
	private final TransactionReportSharedUtil sharedUtil;
	private final ITransactionEntityRepository transactionEntityRepository;

	@Override
	public byte[] getCollectionReportByHospital( TransactionReportRequest request ) {
		Specification<TransactionEntity> specification = _checkIsValidThenMapToSpecification( request );
		List<TransactionEntity> list = this.transactionEntityRepository.getAllBySpecification( specification );
		return this._exportDailyCollectionReportDataToBytes( request, list);
	}

	private Specification<TransactionEntity> _checkIsValidThenMapToSpecification( TransactionReportRequest request) {
		_checkIsValidForDailyCollectionOrThrow( request );
		return _mapRequestToTransactionSpecification( request );
	}

	private void _checkIsValidForDailyCollectionOrThrow( TransactionReportRequest request){
		this.sharedUtil.checkIsValidOrThrow(request);
		_ifHospitalHasSoftwareCheckIsValidRequestOrThrow( request);
	}

	private void _ifHospitalHasSoftwareCheckIsValidRequestOrThrow( TransactionReportRequest request){
		if (! _isHospitalUsingSoftware( request ) ) {
			if ( isEmpty( request.getRevenueHead() ) || isEmpty( request.getRevenueHead().getTitle() ) ){
				throw new InvalidRequestException( NO_REVENUE_HEAD );
			}
		}
	}

	private Specification<TransactionEntity> _mapRequestToTransactionSpecification( TransactionReportRequest request){
		TransactionSearchRequest searchRequest = new TransactionSearchRequest();
		searchRequest.setHospital( request.getHospital() );
		searchRequest.setDateRange( request.getDateRange() );
		searchRequest.setStatus( request.getStatus() );
		return TransactionSearchSpecification.byCriteria( searchRequest);
	}

	private byte[] _exportDailyCollectionReportDataToBytes( TransactionReportRequest request, List<TransactionEntity> list){
		if ( _isHospitalUsingSoftware( request ) ){
			return this.withoutRevenueHeadUtil.export( new BiParam<>( request, list ) );
		}
		return this.withRevenueHeadUtil.export( new BiParam<>( request, list ) );
	}

	private boolean _isHospitalUsingSoftware( TransactionReportRequest request) {
		return request.getHospital().getHasHospitalSoftware();
	}

}
