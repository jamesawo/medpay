/*
 * @Author: james.junior
 * @Date: 7/5/23 12:27
 *
 * @Project: medpay-api
 */

package com.james.medpay.features.billing.domain.entity;

import com.james.medpay.features.billing.domain.enums.BillStatus;
import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.features.users.domain.entity.UserEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.List;

import static com.james.medpay.core.constant.DatabaseTables.BILL_TABLE;

@Data
@Builder
@Entity
@Table( name = BILL_TABLE )
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EntityListeners( AuditingEntityListener.class )
public class PatientBill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	private UserEntity createdBy;

	@OneToOne
	private Patient patient;

	@OneToOne
	private HospitalEntity hospital;

	@OneToMany(mappedBy = "bill")
	private List<PatientBillItem> items;

	@Column(nullable = false, unique = true)
	private String billNumber;
	private Double billAmount;
	private LocalDate createdAt = LocalDate.now();
	private BillStatus status = BillStatus.UNPAID;
	private Integer billAtomic;

	@PrePersist()
	private void beforeSave(){
		this.createdAt = LocalDate.now();
	}



}
