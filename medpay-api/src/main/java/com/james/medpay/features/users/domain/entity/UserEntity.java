
package com.james.medpay.features.users.domain.entity;

import com.james.medpay.features.authentication.domain.entity.Role;
import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.features.users.domain.entity.contract.IUserEntity;
import com.james.medpay.features.users.domain.entity.enums.AddressTypeEnum;
import com.james.medpay.features.users.domain.entity.enums.TrimPatternEnum;
import com.james.medpay.features.users.domain.entity.enums.UserTypeEnum;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

import static com.james.medpay.core.constant.DatabaseTables.USER_ROLE_TABLE;
import static com.james.medpay.core.constant.DatabaseTables.USER_TABLE;
import static com.james.medpay.features.users.domain.entity.enums.UserTypeEnum.REGULAR_USER;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Builder
@Data
@Entity
@EqualsAndHashCode( callSuper = false )
@AllArgsConstructor
@NoArgsConstructor
@Table( name = USER_TABLE )
@ToString
@Validated
@EntityListeners( AuditingEntityListener.class )
public class UserEntity implements IUserEntity {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	@NotNull( message = "Nickname cannot be empty" )
	@Column( nullable = false )
	private String nickName;

	@NotNull( message = "Username/Email cannot be empty" )
	@Column( nullable = false, unique = true )
	private String email;

	@NotNull( message = "Password cannot be empty" )
	@Column( nullable = false )
	private String password;

	@Column( nullable = false, updatable = false, unique = true )
	private String uuid;

	@Column
	private Boolean isEnabled = true;

	@Column
	private LocalDate expiryDate;

	@Column
	private UserTypeEnum userTypeEnum = REGULAR_USER;

	@Column
	private String profilePicUrl;

	@OneToOne
	@JoinColumn
	private UserAddressEntity address;

	@OneToOne
	@JoinColumn
	private UserBasicDetailsEntity basicDetails;

	@OneToOne
	@JoinColumn
	private UserAddressEntity emergencyAddress;

	@OneToOne
	@JoinColumn
	private UserConfigurationEntity configuration;

	@ManyToOne
	@JoinColumn
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

	public UserEntity( Long id ) {
		this.id = id;
	}

	@PrePersist
	void setUUID() {
		this.uuid = UUID.randomUUID().toString();
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		this.password = encoder.encode( password );
		if ( this.expiryDate == null ) {
			this.expiryDate = LocalDate.now().plusMonths( 6 );
		}
	}

	@ManyToMany( fetch = FetchType.EAGER )
	@JoinTable( name = USER_ROLE_TABLE,
			joinColumns = @JoinColumn( name = "user_id" ),
			inverseJoinColumns = @JoinColumn( name = "role_id" ) )
	private Collection<Role> roles;

	public void trim( TrimPatternEnum pattern ) {
		switch ( pattern ) {
			case FULL:
				this.password = null;
				this.address = null;
				this.emergencyAddress = null;
				this.configuration = null;
				this.basicDetails = null;
				this.hospital = null;
				if ( isNotEmpty( this.roles ) ) {
					this.roles.forEach( Role::trim );
				}
				break;
			case PARTIAL:
				this.password = null;
				this.emergencyAddress = null;
				this.configuration = null;
				if ( isNotEmpty( this.hospital ) && isNotEmpty( this.hospital.getId() ) ) {
					this.hospital = HospitalEntity.builder().id( this.hospital.getId() ).build();
				}
				if ( isNotEmpty( this.roles ) ) {
					this.roles.forEach( Role::trim );
				}
				break;
		}
	}

	public void updateAddressIsVerified( boolean isVerified, AddressTypeEnum type ) {
		switch ( type ) {
			case FIRST:
				if ( this.getAddress() != null ) {
					this.getAddress().setIsVerified( isVerified );
				}
				break;
			case EMERGENCY:
				if ( this.getEmergencyAddress() != null ) {
					this.getEmergencyAddress().setIsVerified( isVerified );
				}
				break;
		}
	}

	public boolean isAccountExpired() {
		return this.getExpiryDate().isBefore( LocalDate.now() );
	}

	public boolean isNotSuperUser() {
		return this.userTypeEnum != UserTypeEnum.SUPER_USER;
	}

	public String getFullName() {
		if ( isNotEmpty( this.basicDetails ) && isNotEmpty( this.basicDetails.getFullName() ) ) {
			return this.basicDetails.getFullName();
		}
		return this.nickName;
	}

	public String getPhoneNumber() {
		if ( isNotEmpty( this.basicDetails ) && isNotEmpty( this.basicDetails.getPhoneNumber() ) ) {
			return this.basicDetails.getPhoneNumber();
		}
		return "";
	}

}
