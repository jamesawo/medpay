package com.james.medpay.features.hospital.domain.entity;

import com.james.medpay.core.constant.DatabaseTables;
import com.james.medpay.core.params.BiParam;
import com.james.medpay.features.hospital.domain.entity.contract.IHospitalDetailContract;
import com.james.medpay.features.hospital.domain.entity.enums.HospitalSupportChannelEnum;
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
@Table( name = DatabaseTables.HOSPITAL_DETAIL_TABLE )
@ToString
public class HospitalDetail implements IHospitalDetailContract {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	@Column( nullable = false, unique = true )
	private String name;

	@Column( unique = true )
	private String code;

	@Column
	private String hospitalLogoUrl;

	@Column
	private String supportName;

	@Column
	private String supportPhone;

	@Column
	private String supportEmail;

	@Column
	private String hospitalAddress;

	@Column
	private HospitalSupportChannelEnum supportChannel;

	public void updateProperties( HospitalDetail detail ) {
		if ( detail.getId() != null ) {
			this.id = detail.getId();
		}
		if ( detail.getName() != null ) {
			this.name = detail.getName();
		}
		if ( detail.getHospitalLogoUrl() != null ) {
			this.hospitalLogoUrl = detail.getHospitalLogoUrl();
		}
		if ( detail.getHospitalAddress() != null ) {
			this.hospitalAddress = detail.getHospitalAddress();
		}
		if ( detail.getSupportName() != null ) {
			this.supportName = detail.getSupportName();
		}
		if ( detail.getSupportPhone() != null ) {
			this.supportPhone = detail.getSupportPhone();
		}
		if ( detail.getSupportEmail() != null ) {
			this.supportEmail = detail.getSupportEmail();
		}
		if ( detail.getSupportChannel() != null ) {
			this.supportChannel = detail.getSupportChannel();
		}
	}


	public BiParam<Boolean, List<String>> checkDetails() {
		boolean status = true;
		List<String> results = new ArrayList<>();

		if ( this.getId() != null ) {
			status = false;
			results.add( "Hospital details is not yet created." );
		}
		if ( this.getName() != null ) {
			status = false;
			results.add( "In hospital details,  name is empty" );
		}
		if ( this.getHospitalLogoUrl() != null ) {
			status = false;
			results.add( "Hospital details logo is empty" );
		}
		if ( this.getHospitalAddress() != null ) {
			status = false;
			results.add( "Hospital details address is empty" );
		}
		if ( this.getSupportName() != null ) {
			status = false;
			results.add( "In Hospital details, support name is empty" );
		}
		if ( this.getSupportPhone() != null ) {
			status = false;
			results.add( "In Hospital details, support phone is empty" );
		}
		if ( this.getSupportEmail() != null ) {
			status = false;
			results.add( "In Hospital details, support email is empty" );
		}
		if ( this.getSupportChannel() != null ) {
			status = false;
			results.add( "In Hospital details, support channel is empty" );
		}

		return new BiParam<>( status, results );
	}

}
