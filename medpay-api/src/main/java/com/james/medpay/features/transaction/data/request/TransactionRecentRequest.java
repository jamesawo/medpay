
package com.james.medpay.features.transaction.data.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.james.medpay.core.util.HelperUtil;
import com.james.medpay.features.transaction.domain.entity.TransactionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude( JsonInclude.Include.NON_NULL )
public class TransactionRecentRequest {
	private String date;
	private List<TransactionRequest> transactions;

	public static TransactionRecentRequest toRequest( Map.Entry<LocalDate, List<TransactionEntity>> mapEntry){
		TransactionRecentRequest request = new TransactionRecentRequest();
		if ( mapEntry != null ){
			request.setDate( HelperUtil.formatDate( mapEntry.getKey() ) );
			List<TransactionRequest> list = mapEntry.getValue().stream().map( TransactionRequest::toRequest ).collect( Collectors.toList() );
			request.setTransactions(list);
		}
		return request;
	}
}
