/*
 * @Author: james.junior
 * @Date: 7/5/23 12:27
 *
 * @Project: medpay-api
 */

package com.james.medpay.features.billing.domain.entity;

import com.james.medpay.features.hospital.domain.entity.HospitalService;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


import static com.james.medpay.core.constant.DatabaseTables.BILL_ITEM_TABLE;

@Data
@Builder
@Entity
@Table( name = BILL_ITEM_TABLE )
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EntityListeners( AuditingEntityListener.class )
public class PatientBillItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	private PatientBill bill;

	@OneToOne
	private HospitalService service;

	private Double amount;

}
