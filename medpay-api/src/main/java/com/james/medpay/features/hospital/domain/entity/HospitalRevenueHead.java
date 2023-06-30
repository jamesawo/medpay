package com.james.medpay.features.hospital.domain.entity;

import com.james.medpay.core.constant.DatabaseTables;
import com.james.medpay.features.hospital.domain.entity.contract.IHospitalRevenueHead;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalServiceCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Entity
@Table( name = DatabaseTables.HOSPITAL_REVENUE_HEAD_TABLE )
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners( AuditingEntityListener.class )
public class HospitalRevenueHead implements IHospitalRevenueHead {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	@Column( nullable = false )
	private String title;

	@Column
	private String code;

	/**
	 * if is enabled is false,
	 * all its dependant reference entity should not be visible to users
	 */
	@Column
	private Boolean isEnabled;

	@ManyToOne
	@JoinColumn( nullable = false )
	private HospitalServiceGroup serviceGroup;

	@OneToMany( mappedBy = "revenueHead", fetch = FetchType.LAZY )
	private List<HospitalService> HospitalServices;

	@Column
	private HospitalServiceCategoryEnum category;

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
