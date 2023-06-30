
package com.james.medpay.features.transaction.domain.entity;

import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.features.transaction.domain.entity.contract.ITransactionEntity;
import com.james.medpay.features.transaction.domain.entity.enums.TransactionStatusEnum;
import com.james.medpay.features.users.domain.entity.UserEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static com.james.medpay.core.constant.DatabaseTables.TRANSACTION_TABLE;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Data
@Builder
@Entity
@TypeDef( name = "json", typeClass = JsonStringType.class )
@EqualsAndHashCode( callSuper = false )
@Table( name = TRANSACTION_TABLE )
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EntityListeners( AuditingEntityListener.class )
public class TransactionEntity implements ITransactionEntity {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	/**
	 * public uuid for interactions and entry points
	 */
	@Column( updatable = false, nullable = false, unique = true )
	private String uuid;

	@Column( nullable = false )
	private LocalDate date;

	@Column( nullable = false )
	private String reference;

	@Column( nullable = false )
	private String token;

	@Column( nullable = false )
	private LocalTime time;

	/**
	 * Amount that is being paid.
	 */
	@Column( nullable = false )
	private BigDecimal amount;

	/**
	 * how many times notification was sent.
	 * <p>
	 * Notification sent to 3rd party hospital software to notify about transaction status
	 */
	@Column
	private Integer notificationCount;

	@ColumnDefault( value = "false" )
	@Column
	private Boolean hasNotifiedHospital;

	/**
	 * Hospital to which this transaction is paid to
	 * <p>
	 * should
	 */
	@NotNull
	@ManyToOne
	@JoinColumn( nullable = false )
	private HospitalEntity hospital;

	/**
	 * Logged-in user account .
	 * <p>
	 * Authenticated User making the request to backend.
	 */
	@NotNull
	@ManyToOne
	@JoinColumn( nullable = false )
	private UserEntity user;

	/**
	 * Status updated based on complete checks.
	 * <p>
	 * if payment gateway was used to make payment,
	 * then check status on payment gate, check status on 3rd party app (if has software)
	 * <p>
	 * with all the above checks, transactionStatus should be decided
	 */
	@Column
	private TransactionStatusEnum statusEnum;

	/**
	 * Details about what is being paid for
	 * <p>
	 * if hospital, this should contain billNumber, bill details etc.
	 * if not has software, this should contain hospitalService, from revenueHead
	 */
	@OneToOne
	@JoinColumn( nullable = false )
	private TransactionPaymentDetail paymentDetail;

	/**
	 * Details about who is paying this transaction
	 * <p>
	 * Payer information like name, patient number -> (if hospital has software), phone number
	 */
	@OneToOne
	@JoinColumn( nullable = false )
	private TransactionPayerDetail payerDetail;

	/**
	 * Split configuration used by scheduler.
	 * <p>
	 * To calculate app transaction shares
	 */
	@OneToOne
	@JoinColumn
	private TransactionSettlement settlement;

	@Type( type = "json" )
	@Column( columnDefinition = "json" )
	private String transactionOthers;

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

	@Override
	@PrePersist
	public void setFields() {
		this.uuid = UUID.randomUUID().toString();
		if ( this.date == null ) {
			this.date = LocalDate.now();
		}
		if ( this.time == null ) {
			this.time = LocalTime.now();
		}
		if ( isEmpty( this.reference ) ) {
			this.reference = RandomStringUtils.randomNumeric( 10 );
		}
		if ( isEmpty( this.token ) ) {
			this.token = RandomStringUtils.randomNumeric( 12 );
		}
	}

	@Override
	public void incrementNotificationCount() {
		Integer count = this.getNotificationCount();
		this.notificationCount = count + 1;
	}

	@Override
	public void trim() {
		this.hospital = null;
		this.user = null;
		paymentDetail.trim();
		this.settlement = null;
	}

	public String getAgentFullName(){
		return this.user.getFullName();
	}

	public String getPayerFullName() {
		return this.payerDetail.getFullName();
	}


}
