package com.james.medpay.features.authentication.data.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.james.medpay.features.authentication.domain.entity.Role;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@JsonInclude( JsonInclude.Include.NON_NULL )
public class RoleRequest {
	private Long id;
	private String uuid;
	@NotNull( message = "Role name cannot be null" )
	@NotEmpty( message = "Role name cannot be empty" )
	private String name;
	private String description;
	private Boolean status;
	private Set<PermissionRequest> permissions;
	private String createdAt;

	public static Role toEntity( RoleRequest request ) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setSkipNullEnabled( true );
		return mapper.map( request, Role.class );
	}

	public static RoleRequest toRequest( Role entity ) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setSkipNullEnabled( true );
		return mapper.map( entity, RoleRequest.class );
	}
}
