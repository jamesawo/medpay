
package com.james.medpay.features.report.data.repositoryImpl;

import com.james.medpay.core.annotation.RepositoryImpl;
import com.james.medpay.core.exception.InvalidRequestException;
import com.james.medpay.core.params.BiParam;
import com.james.medpay.features.report.data.repositoryImpl.util.TransactionReportForAgentUtil;
import com.james.medpay.features.report.data.repositoryImpl.util.TransactionReportSharedUtil;
import com.james.medpay.features.report.data.request.TransactionReportRequest;
import com.james.medpay.features.report.domain.repository.transaction.IReportForTransactionByAgentRepository;
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
public class ReportForTransactionByAgentRepositoryImpl implements IReportForTransactionByAgentRepository {

	public static final String NO_USER = "User is required.";
	private final TransactionReportSharedUtil sharedUtil;
	private final ITransactionEntityRepository transactionEntityRepository;
	private final TransactionReportForAgentUtil agentUtil;


	@Override
	public byte[] getCollectionReportByAgent( TransactionReportRequest request ) {
		Specification<TransactionEntity> specification = _checkIsValidThenMapToSpecification( request );
		List<TransactionEntity> list = this.transactionEntityRepository.getAllBySpecification( specification );
		return this._exportDailyCollectionReportDataToBytes( request, list);
	}

	private Specification<TransactionEntity> _checkIsValidThenMapToSpecification( TransactionReportRequest request) {
		_checkIsValidForDailyCollectionOrThrow( request );
		return _mapRequestToTransactionSpecification( request );
	}

	private Specification<TransactionEntity> _mapRequestToTransactionSpecification( TransactionReportRequest request){
		TransactionSearchRequest searchRequest = new TransactionSearchRequest();
		searchRequest.setUser( request.getUser() );
		searchRequest.setDateRange( request.getDateRange() );
		searchRequest.setStatus( request.getStatus() );
		searchRequest.setHospital( request.getHospital() );
		return TransactionSearchSpecification.byCriteria( searchRequest);
	}

	private void _checkIsValidForDailyCollectionOrThrow( TransactionReportRequest request){
		if ( isEmpty( request.getUser() ) || isEmpty( request.getUser().getId() ) ){
			throw new InvalidRequestException( NO_USER );
		}
		this.sharedUtil.checkIsValidOrThrow( request );
	}

	private byte[] _exportDailyCollectionReportDataToBytes( TransactionReportRequest request, List<TransactionEntity> list){
		return this.agentUtil.export( new BiParam<>( request, list ) );
	}
}
