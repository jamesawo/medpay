
package com.james.medpay.features.report.data.repositoryImpl.util;

import com.james.medpay.core.contract.DataExporter;
import com.james.medpay.core.params.BiParam;
import com.james.medpay.core.util.ExportUtil;
import com.james.medpay.core.util.HelperUtil;
import com.james.medpay.features.report.data.model.TransactionServiceGroupPayload;
import com.james.medpay.features.report.data.model.TransactionServicePayload;
import com.james.medpay.features.report.data.request.TransactionReportRequest;
import com.james.medpay.features.transaction.domain.entity.TransactionEntity;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.james.medpay.core.util.HelperUtil.formatDate;
import static com.james.medpay.features.transaction.data.datasource.enums.TransactionFilesEnum.TRANSACTION_DAILY_COLLECTION_REPORT;
import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class TransactionReportWithoutRevenueHeadUtil implements DataExporter<byte[], BiParam<TransactionReportRequest, List<TransactionEntity>>> {

	public static final String COLLECTION_REPORT_TITLE = "HOSPITAL COLLECTION REPORT GROUPED BY DATE <br> FROM ";

	private final TransactionReportSharedUtil sharedUtil;

	private Double totalSumAmount = 0.0;


	@Override
	public byte[] export(
			BiParam<TransactionReportRequest, List<TransactionEntity>> biParam
	) {
		List<TransactionServiceGroupPayload> list = _filterAndMapDataToListOfTransactionServiceGroupPayload( biParam.getRight() );
		HashMap<String, Object> hashMap = _getReportDefaultDataMap( biParam.getLeft() );
		return ExportUtil.generatePDFBytes( hashMap, TRANSACTION_DAILY_COLLECTION_REPORT.asInputStream(), new JRBeanCollectionDataSource( list ) );
	}

	private HashMap<String, Object> _getReportDefaultDataMap( TransactionReportRequest reportRequest ) {
		String startDate = formatDate( reportRequest.getDateRange().getStartDate() ).toUpperCase();
		String endDate = formatDate( reportRequest.getDateRange().getEndDate() ).toUpperCase();
		String reportTitle = String.format( COLLECTION_REPORT_TITLE + "%s - %s", startDate, endDate );
		return this.sharedUtil.getReportMapData( reportRequest, reportTitle, totalSumAmount );
	}

	private List<TransactionServiceGroupPayload> _filterAndMapDataToListOfTransactionServiceGroupPayload( List<TransactionEntity> list ) {
		List<TransactionEntity> filteredList = list.stream().filter( filterPredicate ).collect( Collectors.toList() );
		return _getReportDataFromList( filteredList );
	}

	private final Predicate<TransactionEntity> filterPredicate = ( transaction ) -> transaction.getHospital().getHasHospitalSoftware();

	private List<TransactionServiceGroupPayload> _getReportDataFromList( List<TransactionEntity> list ) {
		this.totalSumAmount = 0.0;
		Map<LocalDate, List<TransactionEntity>> collect = list.stream().collect( groupingBy( TransactionEntity::getDate ) );
		return collect.entrySet().stream().map( this::_transformFromMapEntrySet ).collect( Collectors.toList() );
	}

	private TransactionServiceGroupPayload _transformFromMapEntrySet( Map.Entry<LocalDate, List<TransactionEntity>> entry ) {
		TransactionServiceGroupPayload serviceGroupPayload = new TransactionServiceGroupPayload();
		serviceGroupPayload.setGroupTitle( formatDate( entry.getKey() ) );
		List<TransactionServicePayload> collect = entry.getValue().stream().map( this::_transformFromTransactionEntity ).collect( Collectors.toList() );
		serviceGroupPayload.setTransactions( new JRBeanCollectionDataSource( collect ) );
		this.totalSumAmount += collect.stream().mapToDouble( TransactionServicePayload::getAmount ).sum();
		return serviceGroupPayload;
	}

	private TransactionServicePayload _transformFromTransactionEntity( TransactionEntity transaction ) {
		TransactionServicePayload servicePayload = new TransactionServicePayload();
		servicePayload.setAmount( transaction.getAmount().doubleValue() );
		servicePayload.setDate( formatDate( transaction.getDate() ) );
		servicePayload.setTime( HelperUtil.formatTime( transaction.getTime() ) );
		servicePayload.setAgentFullName( transaction.getAgentFullName() );
		servicePayload.setPayerFullName( transaction.getPayerFullName() );
		return servicePayload;
	}

}
