
package com.james.medpay.features.users.domain.entity;

import com.james.medpay.features.users.domain.entity.contract.IUserConfigurationEntity;
import com.james.medpay.features.users.domain.entity.enums.UserLimitTypeEnum;
import lombok.*;

import javax.persistence.*;


import static com.james.medpay.core.constant.DatabaseTables.USER_CONFIGURATION_TABLE;

@Builder
@Data
@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table( name = USER_CONFIGURATION_TABLE )
@ToString
public class UserConfigurationEntity implements IUserConfigurationEntity {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	@Column
	private Boolean isEnabled = false;

	@Column
	private Integer maxLimit;

	@Column
	private Integer minLimit;

	@Column
	private UserLimitTypeEnum limitType;
}
