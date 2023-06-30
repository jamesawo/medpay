
package com.james.medpay.features.users.data.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.james.medpay.core.contract.DataMapper;
import com.james.medpay.features.users.domain.entity.UserBasicDetailsEntity;
import com.james.medpay.features.users.domain.entity.contract.IUserBasicDetailsEntity;
import com.james.medpay.features.users.domain.entity.enums.GenderEnum;
import com.james.medpay.features.users.domain.entity.enums.UserPreferredNameEnum;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
@JsonInclude( JsonInclude.Include.NON_NULL )
public class UserBasicDetailsRequest implements DataMapper<IUserBasicDetailsEntity, UserBasicDetailsRequest> {
	private Long id;
	private String phoneNumber;
	private String firstName;
	private String lastName;
	private String otherName;
	private GenderEnum gender;
	private String preferredPronoun;
	private UserPreferredNameEnum preferredName;
	private String socialFb;
	private String socialIg;
	private String socialLkd;
	private String personalStatement;
	private String emergencyPhone;

	public UserBasicDetailsRequest() {
	}

	public UserBasicDetailsRequest( Long id, String firstName ) {
		this.id = id;
		this.firstName = firstName;
	}

	@Override
	public IUserBasicDetailsEntity toEntity(
			UserBasicDetailsRequest request
	) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setSkipNullEnabled( true );
		return mapper.map( request, UserBasicDetailsEntity.class);
	}

	@Override
	public UserBasicDetailsRequest toRequest(
			IUserBasicDetailsEntity entity
	) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setSkipNullEnabled( true );
		return mapper.map( entity, UserBasicDetailsRequest.class );
	}

	public static UserBasicDetailsRequest trimToRequest(IUserBasicDetailsEntity entity){
		UserBasicDetailsRequest request = new UserBasicDetailsRequest();
		request.setFirstName( entity.getFirstName() );
		request.setLastName( entity.getLastName() );
		request.setOtherName( entity.getOtherName() );
		return request;
	}
}
