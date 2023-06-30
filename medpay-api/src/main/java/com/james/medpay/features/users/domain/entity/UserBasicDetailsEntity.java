
package com.james.medpay.features.users.domain.entity;

import com.james.medpay.features.users.domain.entity.contract.IUserBasicDetailsEntity;
import com.james.medpay.features.users.domain.entity.enums.GenderEnum;
import com.james.medpay.features.users.domain.entity.enums.UserPreferredNameEnum;
import lombok.*;

import javax.persistence.*;

import static com.james.medpay.core.constant.DatabaseTables.USER_BASIC_DETAILS_TABLE;

@Builder
@Data
@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table( name = USER_BASIC_DETAILS_TABLE )
@ToString
public class UserBasicDetailsEntity implements IUserBasicDetailsEntity {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	@Column
	private String phoneNumber;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private String otherName;

	@Column
	private GenderEnum gender;

	@Column
	private String preferredPronoun;

	@Column
	private UserPreferredNameEnum preferredName;

	@Column
	private String socialFb;

	@Column
	private String socialIg;

	@Column
	private String socialLkd;

	@Column
	private String personalStatement;

	@Column
	private String emergencyPhone;

	public String getFullName() {
		String firstName = this.firstName != null ? this.firstName : "";
		String lastName = this.lastName != null ? this.lastName : "";
		String otherName = this.otherName != null ? this.otherName: "";
		return String.format( "%s %s %s", firstName, lastName, otherName );
	}
}
