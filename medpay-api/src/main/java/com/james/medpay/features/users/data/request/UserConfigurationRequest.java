
package com.james.medpay.features.users.data.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.james.medpay.core.contract.DataMapper;
import com.james.medpay.features.users.domain.entity.UserConfigurationEntity;
import com.james.medpay.features.users.domain.entity.enums.UserLimitTypeEnum;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
@JsonInclude( JsonInclude.Include.NON_NULL )
public class UserConfigurationRequest implements DataMapper<UserConfigurationEntity, UserConfigurationRequest> {
	private Long id;
	private Boolean isEnabled;
	private Integer maxLimit;
	private Integer minLimit;
	private UserLimitTypeEnum limitType;

	public UserConfigurationRequest() {
	}

	public UserConfigurationRequest( Long id ) {
		this.id = id;
	}

	@Override
	public UserConfigurationEntity toEntity(
			UserConfigurationRequest request
	) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setSkipNullEnabled( true );
		return modelMapper.map( request, UserConfigurationEntity.class);
	}

	@Override
	public UserConfigurationRequest toRequest(
			UserConfigurationEntity entity
	) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setSkipNullEnabled( true );
		return modelMapper.map(entity, UserConfigurationRequest.class);
	}
}
