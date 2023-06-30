package com.james.medpay.features.hospital.domain.entity;

import com.james.medpay.core.constant.DatabaseTables;
import com.james.medpay.core.params.BiParam;
import com.james.medpay.features.hospital.domain.entity.contract.IHospitalApiConfiguration;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalAuthTypeEnum;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalEnvironmentEnum;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalMethodEnum;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Builder
@Data
@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table( name = DatabaseTables.HOSPITAL_API_CONFIG_TABLE )
@ToString
public class HospitalApiConfiguration implements IHospitalApiConfiguration {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	@Column
	private String apiBaseUrl;

	@Column
	private String apiTestBaseUrl;

	@Column
	private HospitalAuthTypeEnum authenticationType;

	/**
	 * For basic auth type
	 * this can be used as username.
	 */
	@Column
	private String publicToken;

	/**
	 * For basic auth type
	 * this can be used as password.
	 */
	@Column
	private String privateToken;

	@Column
	private String apiKey;

	@Column
	private String oauthToken;

	@Column( nullable = false)
	private HospitalEnvironmentEnum environmentEnum;


	@Column(length = 32, columnDefinition = "varchar(32) default 'GET'")
	@Enumerated(value = EnumType.STRING)
	private HospitalMethodEnum searchBillMethod;


	@Column(length = 32, columnDefinition = "varchar(32) default 'POST'")
	@Enumerated(value = EnumType.STRING)
	private HospitalMethodEnum payBillMethod;

	@Column
	private String searchBillEndpoint;

	@Column
	private String payBillEndpoint;


	public BiParam<Boolean, List<String>> checkConfiguration() {
		boolean status = true;
		List<String> results = new ArrayList<>();

		if ( this.apiBaseUrl == null || this.apiBaseUrl.isEmpty() ) {
			status = false;
			results.add( "Hospital api config base url is empty" );
		}

		if ( this.authenticationType == null ) {
			status = false;
			results.add( "Hospital api config authentication type is empty" );
		}

		return new BiParam<>( status, results );

	}

	@PrePersist
	void beforeSave(){
		if  (environmentEnum == null) {
			this.environmentEnum = HospitalEnvironmentEnum.LIVE;
		}
	}

}
