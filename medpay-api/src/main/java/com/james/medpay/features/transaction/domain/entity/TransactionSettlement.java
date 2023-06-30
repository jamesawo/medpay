
package com.james.medpay.features.transaction.domain.entity;

import com.james.medpay.features.transaction.domain.entity.contract.ITransactionSettlement;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

import static com.james.medpay.core.constant.DatabaseTables.TRANSACTION_SETTLEMENT_TABLE;

@Builder
@Data
@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table( name = TRANSACTION_SETTLEMENT_TABLE )
@ToString
public class TransactionSettlement implements ITransactionSettlement {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	@Column
	private BigDecimal split;

	@Column
	private String splitType;
	

}

