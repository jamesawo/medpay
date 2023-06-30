
package com.james.medpay.features.transaction.data.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.james.medpay.features.hospital.data.request.HospitalRequest;
import com.james.medpay.features.transaction.domain.entity.TransactionEntity;
import com.james.medpay.features.transaction.domain.entity.enums.TransactionStatusEnum;
import com.james.medpay.features.users.data.request.UserRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static com.james.medpay.core.constant.Routes.API_PREFIX;
import static com.james.medpay.core.constant.Routes.TRANSACTION_ENDPOINT;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude( JsonInclude.Include.NON_NULL )
public class TransactionRequest {
	private Long id;
	private String uuid;
	private LocalDate date;
	private String reference;
	private String token;
	private LocalTime time;
	@NotNull( message = "Amount cannot be null" )
	private BigDecimal amount;
	private Integer notificationCount;
	private Boolean hasNotifiedHospital;
	private TransactionStatusEnum statusEnum;
	private HospitalRequest hospital;
	@NotNull( message = "User cannot be null" )
	private UserRequest user;
	@NotNull( message = "Payment details cannot be null" )
	private TransactionPaymentDetailRequest paymentDetail;
	@NotNull( message = "Payer details cannot be null" )
	private TransactionPayerDetailRequest payerDetail;
	private TransactionSettlementRequest settlement;
	private String receiptUrl;

	public static TransactionRequest toRequest( TransactionEntity entity ) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setSkipNullEnabled( true );
		return mapper.map( entity, TransactionRequest.class );
	}

	public static TransactionEntity toEntity( TransactionRequest request ) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setSkipNullEnabled( true );
		return mapper.map( request, TransactionEntity.class );
	}

	public static TransactionRequest trimToRequest( TransactionEntity entity ) {
		TransactionRequest request = new TransactionRequest();
		request.setId( entity.getId() );
		request.setDate( entity.getDate() );
		request.setReference( entity.getReference() );
		request.setToken( entity.getToken() );
		request.setTime( entity.getTime() );
		request.setAmount( entity.getAmount() );
		request.setStatusEnum( entity.getStatusEnum() );
		request.setHospital( HospitalRequest.trimRequest( entity.getHospital() ) );
		request.setUser( UserRequest.trimToRequest( entity.getUser() ) );
		request.setPaymentDetail( TransactionPaymentDetailRequest.trimToRequest( entity.getPaymentDetail() ) );
		request.setPayerDetail( TransactionPayerDetailRequest.trimToRequest( entity.getPayerDetail() ) );
		if ( entity.getStatusEnum().equals( TransactionStatusEnum.SUCCESSFUL ) ) {
			request.setReceiptUrl( getReceiptDownloadUrl( entity.getReference(), entity.getToken() ) );
		}
		return request;
	}

	public static String getReceiptDownloadUrl( String reference, String token ) {
		String serverUri = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
		String base = serverUri + API_PREFIX + TRANSACTION_ENDPOINT;
		return String.format( "%s/pdf-receipt?ref=%s&token=%s", base, reference, token );
	}

}
