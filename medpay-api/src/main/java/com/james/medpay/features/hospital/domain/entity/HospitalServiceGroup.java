package com.james.medpay.features.hospital.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.james.medpay.core.constant.DatabaseTables;
import com.james.medpay.core.params.BiParam;
import com.james.medpay.features.hospital.domain.entity.contract.IHospitalServiceGroup;
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
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Builder
@Data
@Entity
@TypeDef( name = "jsonb", typeClass = JsonBinaryType.class )
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table( name = DatabaseTables.HOSPITAL_SERVICE_GROUP_TABLE )
@ToString
@EntityListeners( AuditingEntityListener.class )
public class HospitalServiceGroup implements IHospitalServiceGroup  {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	@Column
	private String code;

	@Column
	private String title;

	/**
	 * if service group is disabled, then all its dependant entity
	 * such as revenueHead, services are automatically disabled and not shown to users
	 * <p>
	 * think of it as a visibility toggle.
	 */
	@Column
	private Boolean isEnabled;

	@OneToMany( mappedBy = "serviceGroup", fetch = LAZY )
	private List<HospitalRevenueHead> revenueHeads;


	@JsonManagedReference
	@OneToOne
	@JoinColumn(nullable = false)
	private HospitalEntity hospital;

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

	public BiParam<Boolean, List<String>> checkServiceGroup() {
		boolean status = true;
		List<String> results = new ArrayList<>();

		if ( !this.isEnabled ) {
			status = false;
			results.add( "Hospital service group is disabled" );
		}

		if ( this.title == null || this.title.isEmpty() ) {
			status = false;
			results.add( "Hospital service group title is empty" );
		}

		if ( revenueHeads.isEmpty() ) {
			status = false;
			results.add( "Hospital service group does not contain any revenue head" );

		}
		else if ( revenueHeads.size() == 1 ) {
			boolean hasServices = revenueHeads.get( 0 ).getHospitalServices().size() > 1;
			if ( !hasServices ) {
				status = false;
				results.add(
						"Hospital service group contains at least 1 revenue head but does not contain any service." );
			}
		}

		return new BiParam<>( status, results );

	}

}
