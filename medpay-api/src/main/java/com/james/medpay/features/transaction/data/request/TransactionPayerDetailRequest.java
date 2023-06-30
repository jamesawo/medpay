
package com.james.medpay.features.transaction.data.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.james.medpay.features.transaction.domain.entity.TransactionPayerDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude( JsonInclude.Include.NON_NULL )
public class TransactionPayerDetailRequest {
	private Long id;
	private String fullName;
	private String phoneNumber;
	private String patientNumber;

	public TransactionPayerDetailRequest( Long id ) {
		this.id = id;
	}

	public static TransactionPayerDetailRequest toRequest( TransactionPayerDetail entity ) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setSkipNullEnabled( true );
		return mapper.map( entity, TransactionPayerDetailRequest.class );
	}

	public static TransactionPayerDetail toEntity( TransactionPayerDetailRequest request ) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setSkipNullEnabled( true );
		return mapper.map( request, TransactionPayerDetail.class );
	}

	public static TransactionPayerDetailRequest trimToRequest( TransactionPayerDetail entity ) {
		TransactionPayerDetailRequest request = new TransactionPayerDetailRequest();
		request.setFullName( entity.getFullName() );
		request.setPhoneNumber( isNotEmpty( entity.getPhoneNumber()) ? entity.getPhoneNumber() : "" );
		request.setPatientNumber( isNotEmpty( entity.getPatientNumber()) ? entity.getPatientNumber() : "" );
		return request;
	}


}
