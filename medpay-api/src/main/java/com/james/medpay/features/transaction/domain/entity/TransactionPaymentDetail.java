
package com.james.medpay.features.transaction.domain.entity;

import com.james.medpay.features.hospital.domain.entity.HospitalService;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalServiceCategoryEnum;
import com.james.medpay.features.transaction.domain.entity.contract.ITransactionPaymentDetail;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

import static com.james.medpay.core.constant.DatabaseTables.TRANSACTION_PAYMENT_TABLE;
import static com.james.medpay.core.constant.DatabaseTables.TRANSACTION_SERVICE;

@Data
@Builder
@Entity
@TypeDef( name = "json", typeClass = JsonStringType.class )
@EqualsAndHashCode
@Table( name = TRANSACTION_PAYMENT_TABLE )
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionPaymentDetail implements ITransactionPaymentDetail {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	@ManyToMany( fetch = FetchType.LAZY )
	@JoinTable( name = TRANSACTION_SERVICE,
			joinColumns = @JoinColumn( name = "transaction_payment_id" ),
			inverseJoinColumns = @JoinColumn( name = "service_id" ) )
	private List<HospitalService> services;

	@Column
	private String billNumber;

	@Column
	private HospitalServiceCategoryEnum category;

	@Type( type = "json" )
	@Column( columnDefinition = "json" )
	private String other;


	@Override
	public void trim() {
		this.services = null;
		this.other = null;
	}
}
