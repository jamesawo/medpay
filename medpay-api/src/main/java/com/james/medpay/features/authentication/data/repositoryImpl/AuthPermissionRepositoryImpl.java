package com.james.medpay.features.authentication.data.repositoryImpl;

import com.james.medpay.core.annotation.RepositoryImpl;
import com.james.medpay.features.authentication.domain.entity.Permission;
import com.james.medpay.features.authentication.domain.entity.contract.IPermission;
import com.james.medpay.features.authentication.domain.repository.contract.IAuthPermissionRepository;
import com.james.medpay.features.authentication.domain.repository.dataRepository.IAuthPermissionDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RepositoryImpl
@RequiredArgsConstructor
public class AuthPermissionRepositoryImpl implements IAuthPermissionRepository {

	private final IAuthPermissionDataRepository dataRepository;

	@Override
	public Set<IPermission> findAll() {
		List<Permission> all = this.dataRepository.findAll();
		return new HashSet<>( all );
	}

	@Transactional
	@Override
	public Optional<Boolean> createMany( Set<IPermission> permissions ) {
		try {
			if ( permissions.isEmpty() ) {
				return Optional.of( false );
			}
			Set<Permission> set = permissions.stream().map( permission -> ( Permission ) permission ).collect( Collectors.toSet() );
			this.dataRepository.saveAll( set );
			return Optional.of( true );
		} catch ( Exception e ) {
			throw new RuntimeException( e.getMessage(), e );
		}

	}
}
