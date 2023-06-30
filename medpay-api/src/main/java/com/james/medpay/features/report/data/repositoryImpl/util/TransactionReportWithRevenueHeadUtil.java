
package com.james.medpay.features.report.data.repositoryImpl.util;

import com.james.medpay.core.contract.DataExporter;
import com.james.medpay.core.params.BiParam;
import com.james.medpay.core.util.ExportUtil;
import com.james.medpay.core.util.HelperUtil;
import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.features.hospital.domain.entity.HospitalRevenueHead;
import com.james.medpay.features.hospital.domain.entity.HospitalService;
import com.james.medpay.features.hospital.domain.usecase.basicDetail.HospitalGetHospitalCompleteDetailUsecase;
import com.james.medpay.features.hospital.domain.usecase.revenueHead.HospitalRevenueHeadGetAllByServiceGroupUsecase;
import com.james.medpay.features.hospital.domain.usecase.revenueHead.HospitalRevenueHeadGetByIdUsecase;
import com.james.medpay.features.report.data.model.TransactionServiceGroupPayload;
import com.james.medpay.features.report.data.model.TransactionServicePayload;
import com.james.medpay.features.report.data.request.TransactionReportRequest;
import com.james.medpay.features.transaction.domain.entity.TransactionEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.james.medpay.core.util.HelperUtil.formatDate;
import static com.james.medpay.features.transaction.data.datasource.enums.TransactionFilesEnum.TRANSACTION_DAILY_COLLECTION_REPORT;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionReportWithRevenueHeadUtil implements DataExporter<byte[], BiParam<TransactionReportRequest, List<TransactionEntity>>> {

	public static final String ALL_REVENUE_HEAD = "ALL";
	public static final String COLLECTION_REPORT_TITLE = "HOSPITAL COLLECTION REPORT GROUPED BY REVENUE HEAD <br> FROM ";

	private final HospitalRevenueHeadGetByIdUsecase revenueHeadGetByIdUsecase;
	private final HospitalRevenueHeadGetAllByServiceGroupUsecase getAllByServiceGroupUsecase;
	private final HospitalGetHospitalCompleteDetailUsecase getHospitalCompleteDetailUsecase;
	private final TransactionReportSharedUtil sharedUtil;

	private List<HospitalRevenueHead> revenueHeadList = new ArrayList<>();
	private Double totalSumAmount = 0.0;

	@Override
	public byte[] export( BiParam<TransactionReportRequest, List<TransactionEntity>> biParam ) {
		TransactionReportRequest reportRequest = biParam.getLeft();
		List<TransactionServiceGroupPayload> list = _getReportData( reportRequest, biParam.getRight() );
		HashMap<String, Object> map = _getReportDefaultDataMap( reportRequest );
		return ExportUtil.generatePDFBytes( map, TRANSACTION_DAILY_COLLECTION_REPORT.asInputStream(), new JRBeanCollectionDataSource( list ) );
	}

	private HashMap<String, Object> _getReportDefaultDataMap( TransactionReportRequest reportRequest ) {
		String startDate = formatDate( reportRequest.getDateRange().getStartDate() ).toUpperCase();
		String endDate = formatDate( reportRequest.getDateRange().getEndDate() ).toUpperCase();
		String reportTitle = String.format( COLLECTION_REPORT_TITLE + "%s - %s" , startDate, endDate);
		return this.sharedUtil.getReportMapData( reportRequest, reportTitle, totalSumAmount );
	}

	private List<TransactionServiceGroupPayload> _getReportData( TransactionReportRequest request, List<TransactionEntity> list ) {
		if ( ALL_REVENUE_HEAD.equals( request.getRevenueHead().getTitle() ) ) {
			return _getCollectionReportGroupedByRevenueHead( request, list );
		}
		else {
			return _getCollectionReportForOneRevenueHead( request, list );
		}
	}

	private List<TransactionServiceGroupPayload> _getCollectionReportGroupedByRevenueHead(
			TransactionReportRequest request, List<TransactionEntity> list
	) {
		_setAllRevenueHead( request );
		return _filterAndMapDataToListOfTransactionServiceGroupPayload( list );
	}

	private void _setAllRevenueHead( TransactionReportRequest request ) {
		Optional<HospitalEntity> optional = this.getHospitalCompleteDetailUsecase.execute( request.getHospital().getId() );
		optional.ifPresent( hospital -> this.revenueHeadList = this.getAllByServiceGroupUsecase.execute( hospital.getServiceGroup().getId() ) );
	}

	private List<TransactionServiceGroupPayload> _filterAndMapDataToListOfTransactionServiceGroupPayload( List<TransactionEntity> list ) {
		List<TransactionEntity> filteredList = list.stream().filter( filterPredicate ).collect( Collectors.toList() );
		return _getReportDataFromList( filteredList );
	}

	// todo: refactor, the data displaying on the report is duplicated and incorrect
	private List<TransactionServiceGroupPayload> _getReportDataFromList( List<TransactionEntity> list ) {
		List<TransactionServiceGroupPayload> finalResult = new ArrayList<>();
		for ( HospitalRevenueHead revenueHead : this.revenueHeadList ) {
			TransactionServiceGroupPayload serviceGroupPayload = new TransactionServiceGroupPayload();
			List<TransactionServicePayload> resultList = new ArrayList<>();
			for ( TransactionEntity transaction : list ) {
				TransactionServicePayload servicePayload = new TransactionServicePayload();
				if ( isNotEmpty( transaction.getPaymentDetail().getServices() ) ) {
					//List<HospitalService> hospitalServices = _filterServiceByRevenueHead( revenueHead, transaction.getPaymentDetail().getServices() );
					//double amountSum = hospitalServices.stream().mapToDouble( HospitalService::getPrice ).sum();
					servicePayload.setAmount( transaction.getAmount().doubleValue() );
					servicePayload.setDate( formatDate( transaction.getDate() ) );
					servicePayload.setTime( HelperUtil.formatTime( transaction.getTime() ) );
					servicePayload.setAgentFullName( transaction.getAgentFullName() );
					servicePayload.setPayerFullName( transaction.getPayerFullName() );
					resultList.add( servicePayload );
				}
			}
			serviceGroupPayload.setGroupTitle( String.format( "REVENUE HEAD:  %s",  revenueHead.getTitle().toUpperCase()) );
			serviceGroupPayload.setTransactions( new JRBeanCollectionDataSource( resultList ) );
			this.totalSumAmount += resultList.stream().mapToDouble( TransactionServicePayload::getAmount ).sum();
			finalResult.add( serviceGroupPayload );
		}
		return finalResult;
	}

	private List<HospitalService> _filterServiceByRevenueHead( HospitalRevenueHead revenueHead, List<HospitalService> services ) {
		return services.stream()
		               .filter( hospitalService -> hospitalService.getRevenueHead().getId().equals( revenueHead.getId() ) )
		               .collect( Collectors.toList() );
	}

	private final Predicate<TransactionEntity> filterPredicate = ( transaction ) -> !transaction.getHospital().getHasHospitalSoftware();

	private List<TransactionServiceGroupPayload> _getCollectionReportForOneRevenueHead(
			TransactionReportRequest request, List<TransactionEntity> list
	) {
		Optional<HospitalRevenueHead> optional = revenueHeadGetByIdUsecase.execute( request.getRevenueHead().getId() );
		optional.ifPresent( ( hospitalRevenueHead ) -> this.revenueHeadList.add( hospitalRevenueHead ) );
		return _filterAndMapDataToListOfTransactionServiceGroupPayload( list );
	}

}
