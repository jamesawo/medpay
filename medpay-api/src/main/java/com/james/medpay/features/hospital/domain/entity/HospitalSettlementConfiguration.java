package com.james.medpay.features.hospital.domain.entity;

import com.james.medpay.core.constant.DatabaseTables;
import com.james.medpay.features.hospital.domain.entity.contract.IHospitalSettlementConfiguration;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalSettlementChargeEnum;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;


@Builder
@Data
@Entity
@TypeDef( name = "jsonb", typeClass = JsonBinaryType.class )
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table( name = DatabaseTables.HOSPITAL_SETTLEMENT_TABLE )
@ToString
public class HospitalSettlementConfiguration implements IHospitalSettlementConfiguration {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;


	@Column
	private HospitalSettlementChargeEnum settlementChargeEnum = HospitalSettlementChargeEnum.EXTRA_CHARGE;

}
