package com.james.medpay.features.authentication.data.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.james.medpay.features.authentication.domain.entity.Permission;
import com.james.medpay.features.authentication.domain.entity.enums.AuthModuleEnum;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
@JsonInclude( JsonInclude.Include.NON_NULL )
public class PermissionRequest {
	private Long id;
	private String name;
	private AuthModuleEnum module;
	private String description;

	public static Permission toEntity( PermissionRequest request ) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setSkipNullEnabled( true );
		return mapper.map( request, Permission.class );
	}

	public static PermissionRequest toRequest( Permission entity ) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setSkipNullEnabled( true );
		return mapper.map( entity, PermissionRequest.class );
	}
}
