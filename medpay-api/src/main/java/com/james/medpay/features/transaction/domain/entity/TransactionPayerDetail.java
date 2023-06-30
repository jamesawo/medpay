
package com.james.medpay.features.transaction.domain.entity;


import com.james.medpay.features.transaction.domain.entity.contract.ITransactionPayerDetail;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

import static com.james.medpay.core.constant.DatabaseTables.TRANSACTION_PAYER_TABLE;


@Builder
@Data
@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@TypeDef( name = "json", typeClass = JsonStringType.class )
@Table( name = TRANSACTION_PAYER_TABLE )
@ToString
public class TransactionPayerDetail implements ITransactionPayerDetail {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	@Column( nullable = false )
	private String fullName;

	@Column
	private String phoneNumber;

	@Column
	private String patientNumber;

	@Type( type = "json" )
	@Column( columnDefinition = "json" )
	private String payerOthers;
	
}
