package com.james.medpay.features.authentication.domain.entity;

import com.james.medpay.features.authentication.domain.entity.contract.IPermission;
import com.james.medpay.features.authentication.domain.entity.enums.AuthModuleEnum;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static com.james.medpay.core.constant.DatabaseTables.PERMISSION_TABLE;

@Data
@Builder
@Entity
@TypeDef( name = "json", typeClass = JsonStringType.class )
@EqualsAndHashCode( callSuper = false )
@Table( name = PERMISSION_TABLE )
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EntityListeners( AuditingEntityListener.class )
public class Permission implements IPermission {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	@Column( nullable = false, unique = true )
	private String name;

	@Column
	private AuthModuleEnum module;

	public Permission( String name, AuthModuleEnum module ) {
		this.name = name;
		this.module = module;
	}
}
