/*
 * @Author: james.junior
 * @Date: 7/5/23 12:13
 *
 * @Project: medpay-api
 */

package com.james.medpay.features.billing.domain.entity;

import com.james.medpay.features.users.domain.entity.enums.GenderEnum;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.time.LocalDate;

import static com.james.medpay.core.constant.DatabaseTables.BILL_PATIENT;

@Data
@Builder
@Entity
@Table( name = BILL_PATIENT )
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EntityListeners( AuditingEntityListener.class )
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String firstName;
	private String lastName;
	private String otherName;
	private LocalDate dateOfBirth;
	private Integer age;
	private String phone;
	private String address;
	private GenderEnum gender;
	private LocalDate createdAt;
	private Integer patientNumber;
	private String patientNumberPrefix = "PN";
	private String uniqueCode;

	@PrePersist()
	private void beforeSave(){
		this.createdAt = LocalDate.now();
	}

	public String getFullName(){
		return String.format("%s %s %s", this.firstName, this.lastName, this.otherName);
	}

}
