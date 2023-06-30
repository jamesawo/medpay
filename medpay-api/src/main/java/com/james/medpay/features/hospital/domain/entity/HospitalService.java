package com.james.medpay.features.hospital.domain.entity;

import com.james.medpay.core.constant.DatabaseTables;
import com.james.medpay.features.hospital.domain.entity.contract.IHospitalService;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@Entity
@TypeDef( name = "jsonb", typeClass = JsonBinaryType.class )
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table( name = DatabaseTables.HOSPITAL_SERVICE_TABLE )
@ToString
@EntityListeners( AuditingEntityListener.class )
public class HospitalService implements IHospitalService {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column
	private String code;

	@Column
	private Double price;

	@Column
	private Boolean isEnabled;

	@ManyToOne( fetch = FetchType.LAZY, optional = false )
	@JoinColumn( nullable = false )
	private HospitalRevenueHead revenueHead;

	@CreatedDate
	@Column( nullable = false, updatable = false )
	private LocalDateTime createdAt;

	@CreatedBy
	@Column
	private String createdBy;

	@LastModifiedDate
	@Column
	private LocalDateTime updatedAt;

	@LastModifiedBy
	@Column
	private String lastUpdatedBy;

	@Column
	private String deletedBy;

	@Column
	private LocalDate deletedAt;

}
