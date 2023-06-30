
package com.james.medpay.features.users.domain.entity;

import com.james.medpay.features.users.domain.entity.contract.IUserAddressEntity;
import lombok.*;

import javax.persistence.*;

import static com.james.medpay.core.constant.DatabaseTables.USER_ADDRESS_TABLE;

@Builder
@Data
@Entity
@EqualsAndHashCode( callSuper = false )
@AllArgsConstructor
@NoArgsConstructor
@Table( name = USER_ADDRESS_TABLE )
@ToString
public class UserAddressEntity implements IUserAddressEntity {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	@Column
	private String state;

	@Column
	private String city;

	@Column
	private String street;

	@Column
	private String currentPlaceOfResidence;

	@Column
	private Boolean isVerified;

}
