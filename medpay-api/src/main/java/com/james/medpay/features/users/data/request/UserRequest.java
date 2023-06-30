
package com.james.medpay.features.users.data.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.james.medpay.core.contract.DataMapper;
import com.james.medpay.features.authentication.data.request.RoleRequest;
import com.james.medpay.features.hospital.data.request.HospitalRequest;
import com.james.medpay.features.users.domain.entity.UserEntity;
import com.james.medpay.features.users.domain.entity.enums.UserTypeEnum;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@JsonInclude( JsonInclude.Include.NON_NULL )
public class UserRequest implements DataMapper<UserEntity, UserRequest> {

	private Long id;

	@NotNull( message = "Nickname is required" )
	private String nickName;

	private String uuid;

	@Email( message = "Provide a valid email address" )
	private String email;

	@NotNull( message = "Password is required" )
	private String password;

	private Boolean isEnabled;
	private LocalDate expiryDate;
	private UserTypeEnum userTypeEnum;
	private UserAddressRequest address;
	private UserBasicDetailsRequest basicDetails;
	private UserAddressRequest emergencyAddress;
	private UserConfigurationRequest configuration;
	private HospitalRequest hospital;
	private String createdAt;
	private List< RoleRequest > roles;

	public UserRequest() {
	}

	public UserRequest( Long id ) {
		this.id = id;
	}

	@Override
	public UserEntity toEntity( UserRequest userRequest ) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setSkipNullEnabled( true );
		return modelMapper.map( userRequest, UserEntity.class );
	}

	@Override
	public UserRequest toRequest( UserEntity user ) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setSkipNullEnabled( true );
		UserRequest request = modelMapper.map( user, UserRequest.class );
		if(ObjectUtils.isNotEmpty( user.getHospital() )) {
			request.setHospital( HospitalRequest.from( user.getHospital() ) );
		}
		removeProps( request );
		return request;
	}

	public static UserRequest trimToRequest( UserEntity user ) {
		UserRequest request = new UserRequest();
		request.setId( user.getId() );
		request.setNickName( user.getNickName() );
		request.setEmail( user.getEmail() );
		request.setBasicDetails( UserBasicDetailsRequest.trimToRequest(user.getBasicDetails()) );
		return request;
	}


	public boolean isTrimmed() {
		return ObjectUtils.isEmpty( this.password ) &&
				ObjectUtils.isEmpty( this.emergencyAddress ) &&
				ObjectUtils.isEmpty( this.configuration ) &&
				ObjectUtils.isEmpty( this.hospital );
	}

	private void removeProps(UserRequest request) {
		request.setPassword( null );
	}

}
