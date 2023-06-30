package com.james.medpay.features.authentication.domain.entity;

import com.james.medpay.features.authentication.domain.entity.contract.IRole;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


import static com.james.medpay.core.constant.DatabaseTables.*;

@Data
@Builder
@Entity
@TypeDef( name = "json", typeClass = JsonStringType.class )
@EqualsAndHashCode( callSuper = false )
@Table( name = ROLE_TABLE )
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EntityListeners( AuditingEntityListener.class )
public class Role implements IRole {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	/**
	 * public uuid for interactions and entry points
	 */
	@Column( updatable = false, nullable = false, unique = true )
	private String uuid;

	@NotNull( message = "Name cannot be null" )
	@Column( unique = true, nullable = false )
	private String name;

	@Column
	private String description;

	@Column( nullable = false )
	private Boolean status;

	@ManyToMany( cascade = CascadeType.MERGE, fetch = FetchType.EAGER )
	@JoinTable( name = ROLE_PERMISSION_TABLE,
			joinColumns = @JoinColumn( name = "role_id" ),
			inverseJoinColumns = @JoinColumn( name = "permission_id" ) )
	private Set<Permission> permissions;


	public Role( String name, Set<Permission> permissions ) {
		this.name = name;
		this.permissions = permissions;
	}

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

	@PrePersist
	public void setUUID() {
		this.uuid = UUID.randomUUID().toString();
		this.status = true;
	}

	@Override
	public boolean isNotSuperRole() {
		return !this.name.equals(DEFAULT_SUPER_ADMIN_ROLE);
	}

	public void trim() {
		this.permissions = null;
	}

}
