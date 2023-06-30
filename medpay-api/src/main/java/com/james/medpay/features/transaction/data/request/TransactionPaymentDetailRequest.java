
package com.james.medpay.features.transaction.data.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.james.medpay.features.hospital.data.request.HospitalServiceRequest;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalServiceCategoryEnum;
import com.james.medpay.features.transaction.domain.entity.TransactionPaymentDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude( JsonInclude.Include.NON_NULL )
public class TransactionPaymentDetailRequest {
	private Long id;
	private String billNumber;
	private HospitalServiceCategoryEnum category;
	private Collection<HospitalServiceRequest> services;

	public TransactionPaymentDetailRequest( Long id ) {
		this.id = id;
	}

	public static TransactionPaymentDetailRequest toRequest( TransactionPaymentDetail entity ) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setSkipNullEnabled( true );
		return mapper.map( entity, TransactionPaymentDetailRequest.class );
	}

	public static TransactionPaymentDetail toEntity( TransactionPaymentDetailRequest request ) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setSkipNullEnabled( true );
		return mapper.map( request, TransactionPaymentDetail.class );
	}

	public static TransactionPaymentDetailRequest trimToRequest( TransactionPaymentDetail entity ) {
		TransactionPaymentDetailRequest request = new TransactionPaymentDetailRequest();
		request.setId( entity.getId() );
		request.setBillNumber( isNotEmpty( entity.getBillNumber()) ? entity.getBillNumber() : ""  );
		if(!entity.getServices().isEmpty()) {
			List<HospitalServiceRequest> collect = entity.getServices()
			                                             .stream()
			                                             .map( HospitalServiceRequest::trimToRequest )
			                                             .collect( Collectors.toList() );
			request.setServices(collect);
		}
		return request;
	}
}
