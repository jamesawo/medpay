package com.james.medpay.features.hospital.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.james.medpay.core.constant.DatabaseTables;
import com.james.medpay.core.params.BiParam;
import com.james.medpay.features.hospital.domain.entity.contract.IHospitalEntityContract;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalCollectionModelEnum;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.james.medpay.features.hospital.domain.entity.enums.HospitalCollectionModelEnum.GATEWAY;
import static com.james.medpay.features.hospital.domain.entity.enums.HospitalCollectionModelEnum.MANUAL;

@Data
@Builder
@Entity
@TypeDef( name = "json", typeClass = JsonStringType.class )
@EqualsAndHashCode( callSuper = false )
@Table( name = DatabaseTables.HOSPITAL_TABLE )
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EntityListeners( AuditingEntityListener.class )
public class HospitalEntity implements IHospitalEntityContract {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;


	/**
	 * public id used for interaction by presenter or entryPoint
	 */
	@Column( updatable = false, unique = true, nullable = false )
	private String uuid;

	/**
	 * if isEnabled is false, this hospital should not be visible
	 */
	@ColumnDefault( value = "true" )
	@Column
	private Boolean isEnabled;

	@Column
	private LocalDate startDate;

	/**
	 * if this is true, then this apiConfiguration property value should not be
	 * null
	 * <p>
	 * else if false then this serviceGroup property should not be null
	 */
	@ColumnDefault( value = "true" )
	@Column
	private Boolean hasHospitalSoftware;

	/**
	 * Third party api configuration properties that this hospital should use when fetching
	 * bill details using a bill or invoice number and getting receipt back after
	 * payment is made.
	 */
	@OneToOne
	@JoinColumn
	private HospitalApiConfiguration apiConfiguration;

	/**
	 * CollectionMode of how this hospital collecting payment from patient.
	 * <p>
	 * This should affect the settlementConfiguration property.
	 */
	@Column
	private HospitalCollectionModelEnum collectionModelEnum;

	/**
	 * if hasHospitalSoftware is false, then service group entity should store
	 * revenue heads for this hospital and each revenue head should have a list of
	 * hospital services, these services will be used to accept payment from
	 * patients.
	 */

	@OneToOne(mappedBy = "hospital")
	@JsonBackReference
	private HospitalServiceGroup serviceGroup;

	/**
	 * Settlement model for each player (agent, app provider, etc.) should live in
	 * the settlement configuration entity.
	 */
	@OneToOne
	@JoinColumn
	private HospitalSettlementConfiguration settlementConfiguration;

	/**
	 * For storing basic hospital information
	 */
	@OneToOne
	@JoinColumn
	private HospitalDetail detail;

	@Type( type = "json" )
	@Column( columnDefinition = "json" )
	private String other;

	@Column
	private LocalDate expiryDate;

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

	public HospitalEntity( Long id ) {
		this.id = id;
	}

	@PrePersist
	void setUUID() {
		this.uuid = UUID.randomUUID().toString();
		this.startDate = LocalDate.now();
	}

	public HospitalEntity trim() {
		this.setApiConfiguration( null );
		this.setSettlementConfiguration( null );
		this.setServiceGroup( null );
		this.setOther( null );
		return this;
	}

	public Map<String, Object> runStateChecker() {
		boolean checkedStatus = true;
		List<String> checkedResult = new ArrayList<>();

		if ( this.isEnabled == null || !this.isEnabled ) {
			checkedResult.add( "Hospital is disabled." );
			checkedStatus = false;
		}

		if ( this.expiryDate == null ) {
			checkedResult.add( "Hospital expiry date is empty" );
			checkedStatus = false;
		}
		else {
			if ( this.expiryDate.isBefore( LocalDate.now() ) ) {
				checkedResult.add( "Hospital expiry date is " + this.expiryDate.toString() );
				checkedStatus = false;
			}
		}
		BiParam<Boolean, List<String>> checkDetails = this.detail.checkDetails();
		BiParam<Boolean, List<String>> checkServiceGroup = null;
		BiParam<Boolean, List<String>> checkConfiguration = null;

		if ( this.collectionModelEnum == MANUAL ) {
			checkServiceGroup = this.serviceGroup.checkServiceGroup();
		}
		else if ( this.collectionModelEnum == GATEWAY ) {
			checkConfiguration = this.apiConfiguration.checkConfiguration();
		}

		boolean service = false;
		boolean config = false;

		if ( checkServiceGroup != null ) {
			service = checkServiceGroup.getLeft();
		}
		if ( checkConfiguration != null ) {
			config = checkConfiguration.getLeft();
		}

		Map<String, Object> map = new HashMap<>();
		boolean result = checkedStatus && checkDetails.getLeft() && service == true && config == true;
		map.put( "result", result );
		map.put( "checkedBase", checkedResult );
		map.put( "checkedDetails", checkDetails.getRight() );
		map.put( "checkServiceGroup",
		         checkServiceGroup == null ? "service group failed." : checkServiceGroup.getRight()
		);
		map.put( "checkConfiguration",
		         checkConfiguration == null ? "api configuration checked failed." : checkConfiguration.getRight()
		);
		return map;
	}

}
