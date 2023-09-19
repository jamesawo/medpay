package com.james.medpay.features.users.data.repositoryImpl;

import com.james.medpay.features.users.domain.entity.UserEntity;
import com.james.medpay.features.users.domain.repository.contract.IUserDetailsRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class UserPrincipalImpl implements UserDetails {
	private final UserEntity user;
	private final IUserDetailsRepository detailsRepository;

	public UserPrincipalImpl( UserEntity user, IUserDetailsRepository detailsRepository ) {
		this.user = user;
		this.detailsRepository = detailsRepository;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<String> permissions = this.detailsRepository.findUserPermissionsNameOnly( this.user );
		return permissions.stream().map( SimpleGrantedAuthority::new ).collect( Collectors.toList() );
	}

	@Override
	public String getPassword() {
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		return this.user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.user.isAccountExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.user.getIsEnabled();
	}

}
