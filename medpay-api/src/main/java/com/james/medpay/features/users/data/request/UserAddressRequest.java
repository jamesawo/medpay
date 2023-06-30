
package com.james.medpay.features.users.data.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.james.medpay.core.contract.DataMapper;
import com.james.medpay.features.users.domain.entity.UserAddressEntity;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
@JsonInclude( JsonInclude.Include.NON_NULL )
public class UserAddressRequest implements DataMapper<UserAddressEntity, UserAddressRequest> {
	private Long id;
	private String state;
	private String city;
	private String street;
	private String currentPlaceOfResidence;
	private Boolean isVerified;

	public UserAddressRequest() {
	}

	public UserAddressRequest( Long id ) {
		this.id = id;
	}

	@Override
	public UserAddressEntity toEntity( UserAddressRequest userAddressRequest ) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setSkipNullEnabled( true );
		return modelMapper.map( userAddressRequest, UserAddressEntity.class );
	}

	@Override
	public UserAddressRequest toRequest( UserAddressEntity addressEntity ) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setSkipNullEnabled( true );
		return modelMapper.map( addressEntity, UserAddressRequest.class );
	}
}
