
package com.james.medpay.features.transaction.data.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.james.medpay.features.transaction.domain.entity.TransactionSettlement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude( JsonInclude.Include.NON_NULL )
public class TransactionSettlementRequest {
	private Long id;
	private BigDecimal split;
	private String splitType;

	public TransactionSettlementRequest( Long id ) {
		this.id = id;
	}

	public static TransactionSettlementRequest toRequest( TransactionSettlement entity ) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setSkipNullEnabled( true );
		return mapper.map( entity, TransactionSettlementRequest.class );
	}

	public static TransactionSettlement toEntity( TransactionSettlementRequest request ) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setSkipNullEnabled( true );
		return mapper.map( request, TransactionSettlement.class );
	}

}
