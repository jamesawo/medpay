
package com.james.medpay.features.report.data.repositoryImpl.util;

import com.james.medpay.core.contract.DataExporter;
import com.james.medpay.core.exception.InvalidRequestException;
import com.james.medpay.core.params.BiParam;
import com.james.medpay.core.util.ExportUtil;
import com.james.medpay.features.report.data.model.TransactionServiceGroupPayload;
import com.james.medpay.features.report.data.model.TransactionServicePayload;
import com.james.medpay.features.report.data.request.TransactionReportRequest;
import com.james.medpay.features.transaction.domain.entity.TransactionEntity;
import com.james.medpay.features.users.domain.entity.UserEntity;
import com.james.medpay.features.users.domain.entity.contract.IUserEntity;
import com.james.medpay.features.users.domain.usecase.usersBaseUsecase.UserGetByIdUsecase;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.james.medpay.core.util.HelperUtil.formatDate;
import static com.james.medpay.core.util.HelperUtil.formatTime;
import static com.james.medpay.features.transaction.data.datasource.enums.TransactionFilesEnum.TRANSACTION_AGENT_COLLECTION_REPORT;
import static java.util.stream.Collectors.groupingBy;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Service
@RequiredArgsConstructor
public class TransactionReportForAgentUtil implements DataExporter<byte[], BiParam<TransactionReportRequest, List<TransactionEntity>>> {

	private final static String COLLECTION_REPORT_TITLE = "AGENT COLLECTION REPORT";
	private final static String AGENT_NAME_TITLE = "AGENT NAME: ";
	private final static String AGENT_PHONE_TITLE = "AGENT PHONE: ";
	private final static String DATE_RANGE_TITLE = "DATE FROM: ";
	private final static String NO_USER = "NO USER FOUND";

	private final TransactionReportSharedUtil sharedUtil;
	private final UserGetByIdUsecase getByIdUsecase;

	private Double totalSumAmount = 0.0;

	@Override
	public byte[] export(
			BiParam<TransactionReportRequest, List<TransactionEntity>> biParam
	) {
		List<TransactionServiceGroupPayload> list = _mapDataToListOfTransactionServiceGroupPayload( biParam.getRight() );
		HashMap<String, Object> map = _getReportDefaultDataMap( biParam.getLeft() );

		JRDataSource jrDataSource = !list.isEmpty() ?  new JRBeanCollectionDataSource(list) : new JREmptyDataSource();

		return ExportUtil.generatePDFBytes( map, TRANSACTION_AGENT_COLLECTION_REPORT.asInputStream(),  jrDataSource);
	}

	private HashMap<String, Object> _getReportDefaultDataMap( TransactionReportRequest reportRequest ) {
		HashMap<String, Object> mapData = this.sharedUtil.getReportMapData( reportRequest, COLLECTION_REPORT_TITLE, totalSumAmount );

		mapData.put( "agentName", _getAgentDetailForReportMap(reportRequest, AGENT_NAME_TITLE));
		mapData.put( "agentPhone", _getAgentDetailForReportMap(reportRequest, AGENT_PHONE_TITLE) );
		mapData.put( "dateRange", _getDateRangeForReportMap( reportRequest ) );
		return mapData;
	}

	private String _getAgentDetailForReportMap(TransactionReportRequest reportRequest, String title){
		Optional<IUserEntity> optionalUser = this.getByIdUsecase.execute( reportRequest.getUser().getId() );
		if ( optionalUser.isPresent() ) {
			IUserEntity iUser = optionalUser.get();
			if ( title.equals( AGENT_NAME_TITLE ) ){
				return String.format( AGENT_NAME_TITLE + " %s ",iUser.getFullName().toUpperCase() ) ;
			}else{
				return String.format( AGENT_PHONE_TITLE + " %s ",iUser.getPhoneNumber().toUpperCase() ) ;
			}
		}
		throw new InvalidRequestException( NO_USER );
	}

	private String _getDateRangeForReportMap(TransactionReportRequest reportRequest){
		String startDate = formatDate( reportRequest.getDateRange().getStartDate() ).toUpperCase();
		String endDate = formatDate( reportRequest.getDateRange().getEndDate() ).toUpperCase();
		return String.format( DATE_RANGE_TITLE + "%s -- %s", startDate, endDate );
	}

	private List<TransactionServiceGroupPayload> _mapDataToListOfTransactionServiceGroupPayload( List<TransactionEntity> list ) {
		this.totalSumAmount = 0.0;
		Map<LocalDate, List<TransactionEntity>> collect = _groupTransactionListByDate(list);
		return collect.entrySet().stream().map( this::_mapToPayloadGroupByDate ).collect( Collectors.toList() );
	}

	private Map<LocalDate, List<TransactionEntity>> _groupTransactionListByDate( List<TransactionEntity> list){
		return list.stream().collect( groupingBy( TransactionEntity::getDate ) );
	}

	private TransactionServiceGroupPayload _mapToPayloadGroupByDate(
			Map.Entry<LocalDate, List<TransactionEntity>> value
	){
		TransactionServiceGroupPayload groupPayload = new TransactionServiceGroupPayload();
		groupPayload.setGroupTitle( formatDate( value.getKey() ) );
		List<TransactionServicePayload> collect = value
				.getValue()
				.stream()
				.map( this::_mapToTransactionServicePayload )
				.collect( Collectors.toList() );

		this.totalSumAmount += collect.stream().mapToDouble( TransactionServicePayload::getAmount ).sum();
		groupPayload.setTransactions( new JRBeanCollectionDataSource( collect ) );
		return groupPayload;
	}

	private TransactionServicePayload _mapToTransactionServicePayload(
			TransactionEntity transaction
	) {
		TransactionServicePayload payload = new TransactionServicePayload();
		payload.setAmount( transaction.getAmount().doubleValue() );
		payload.setTime( formatTime( transaction.getTime() ) );
		payload.setDate( formatDate( transaction.getDate() ) );
		payload.setAgentFullName( transaction.getAgentFullName() );
		payload.setPayerFullName( transaction.getPayerFullName().toLowerCase() );
		return payload;
	}

	private Map<UserEntity, List<TransactionEntity>> _groupTransactionListByUser( List<TransactionEntity> list){
		return list.stream().collect( groupingBy( TransactionEntity::getUser ) );
	}

	private TransactionServiceGroupPayload _mapToPayloadGroupByUser(
			Map.Entry<UserEntity, List<TransactionEntity>> value
	) {
		TransactionServiceGroupPayload groupPayload = new TransactionServiceGroupPayload();
		UserEntity user = value.getKey();
		groupPayload.setGroupTitle( isNotEmpty( user.getFullName() ) ? user.getFullName().toUpperCase() : user.getNickName().toUpperCase() );
		List<TransactionServicePayload> collect = value
				.getValue()
				.stream()
				.map( this::_mapToTransactionServicePayload )
				.collect( Collectors.toList() );

		this.totalSumAmount += collect.stream().mapToDouble( TransactionServicePayload::getAmount ).sum();
		groupPayload.setTransactions( new JRBeanCollectionDataSource( collect ) );
		return groupPayload;
	}


}
