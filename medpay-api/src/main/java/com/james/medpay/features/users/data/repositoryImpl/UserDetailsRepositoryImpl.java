
package com.james.medpay.features.users.data.repositoryImpl;

import com.james.medpay.core.annotation.RepositoryImpl;
import com.james.medpay.features.authentication.domain.entity.Permission;
import com.james.medpay.features.authentication.domain.entity.Role;
import com.james.medpay.features.users.domain.entity.UserEntity;
import com.james.medpay.features.users.domain.entity.contract.IUserEntity;
import com.james.medpay.features.users.domain.repository.contract.IUserDetailsRepository;
import com.james.medpay.features.users.domain.usecase.usersBaseUsecase.UserGetByEmailUsecase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RepositoryImpl
public class UserDetailsRepositoryImpl implements IUserDetailsRepository, UserDetailsService {

	@Autowired
	private UserGetByEmailUsecase usecase;

	private static final String NO_USER_FOUND = "No user found ";


	@Override
	public UserEntity findByUsername( String username ) {
		Optional<IUserEntity> optional = this.usecase.execute( username );
		return optional.map( user -> ( UserEntity ) user ).orElseThrow( () -> new UsernameNotFoundException( NO_USER_FOUND ) );

	}

	@Override
	public List<String> findUserPermissionsNameOnly( UserEntity user ) {
		Collection<Permission> permissions = this.findUserPermissionsFromRoles( user.getRoles() );
		return permissions.stream().map( Permission::getName ).collect( Collectors.toList() );
	}

	@Override
	public Collection<Permission> findUserPermissions( UserEntity user ) {
		Collection<Role> roles = user.getRoles();
		return roles.stream().flatMap( role -> role.getPermissions().stream() ).collect( Collectors.toList() );
	}

	@Override
	public Collection<Permission> findUserPermissionsFromRoles( Collection<Role> roles ) {
		Collection<Permission> permissions = new ArrayList<>();
		roles.stream().map( Role::getPermissions ).forEachOrdered( permissions::addAll );
		return permissions;
	}

	// UserDetailsService implementation
	@Override
	public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
		Optional<IUserEntity> optional = this.usecase.execute( username );
		return optional.map( iUser -> new User( iUser.getEmail(), iUser.getPassword(),
		                                        true, true,
		                                        true, true,
		                                        getAuthorities( iUser.getRoles() )
		) ).orElseThrow( () -> new UsernameNotFoundException( NO_USER_FOUND + username ) );
	}

	private Collection<? extends GrantedAuthority> getAuthorities( Collection<Role> roles ) {
		return getGrantedAuthorities( getPrivileges( roles ) );
	}

	private List<String> getPrivileges( Collection<Role> roles ) {
		List<String> privileges;
		List<Permission> collection = roles.stream().flatMap( role -> role.getPermissions().stream() ).collect( Collectors.toList() );
		privileges = collection.stream().map( Permission::getName ).collect( Collectors.toList() );
		return privileges;
	}

	private List<GrantedAuthority> getGrantedAuthorities( List<String> privileges ) {
		return privileges.stream().map( SimpleGrantedAuthority::new ).collect( Collectors.toList() );
	}

}
