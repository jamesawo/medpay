package com.james.medpay.features.authentication.data.repositoryImpl;

import com.james.medpay.core.annotation.RepositoryImpl;
import com.james.medpay.core.exception.InvalidRequestException;
import com.james.medpay.features.authentication.domain.entity.Permission;
import com.james.medpay.features.authentication.domain.entity.Role;
import com.james.medpay.features.authentication.domain.entity.contract.IPermission;
import com.james.medpay.features.authentication.domain.entity.contract.IRole;
import com.james.medpay.features.authentication.domain.repository.contract.IAuthRoleRepository;
import com.james.medpay.features.authentication.domain.repository.dataRepository.IAuthRoleDataRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.james.medpay.core.constant.DatabaseTables.DEFAULT_SUPER_ADMIN_ROLE;
import static java.util.Optional.of;

@RepositoryImpl
@RequiredArgsConstructor
public class AuthRoleRepositoryImpl implements IAuthRoleRepository {

	public static final String DUPLICATE_ROLE = "Possible Duplicate: Role with similar name already exist";

	private final IAuthRoleDataRepository dataRepository;

	@Override
	public IRole create( IRole irole ) {
		_checkRoleExistBeforeCreating( irole.getName() );
		return dataRepository.save( ( Role ) irole );
	}

	private void _checkRoleExistBeforeCreating(String title){
		Optional<Role> optional = this.dataRepository.findByName( title );
		if ( optional.isPresent() ){
			throw new InvalidRequestException( DUPLICATE_ROLE );
		}
	}

	@Override
	public List<IRole> getAll() {
		List<Role> list = dataRepository.findAll();
		return _filterRoleList( list );
	}

	@Override
	public Optional<IRole> getById( Long id ) {
		return _findById( id );
	}

	@Override
	public List<IRole> getByName( String name ) {
		List<Role> list = dataRepository.findByNameIsContainingIgnoreCase( name );
		return _filterRoleList( list );
	}

	private List<IRole> _filterRoleList(List<Role> list){
		return list.stream().filter( role -> !role.getName().equals( DEFAULT_SUPER_ADMIN_ROLE ) ).collect( Collectors.toList() );
	}

	@Override
	public Optional<Integer> getUsageCount( Long id ) {
		// Optional<IRole> optional = _findById( id );
		// todo:: implement feature, use userUsecase to find usage
		return Optional.empty();
	}

	@Override
	public List<IPermission> getPermissions( Long id ) {
		Optional<IRole> optional = _findById( id );
		return optional.map( role -> new ArrayList<IPermission>( role.getPermissions() ) ).orElseGet( ArrayList::new );
	}

	@Override
	public Optional<Boolean> getStatusId( Long id ) {
		Optional<IRole> optional = _findById( id );
		return optional.map( IRole::getStatus );
	}

	@Override
	public Optional<Boolean> getCanDisableFlag( Long id ) {
		Optional<Integer> count = this.getUsageCount( id );
		return count.map( result -> result < 1 );
	}

	@Override
	public Optional<IRole> updateNameAndStatus( Long id, IRole role ) {
		Optional<IRole> optional = this._findById( id );
		return optional.map( existingRole -> _setNameAndStatus( existingRole, role ) );
	}

	private Role _setNameAndStatus( IRole old, IRole newRole){
		old.setName( newRole.getName() );
		old.setStatus( newRole.getStatus() );
		return this.dataRepository.save( (Role ) old );
	}

	@Override
	public Optional<Boolean> updateStatusById( Long id, boolean status ) {
		Optional<IRole> optional = this._findById( id );
		return optional.map( role -> {
			role.setStatus( status );
			this.dataRepository.save( ( Role ) role );
			return of( true );
		} ).orElseGet( () -> of( false ) );
	}

	@Override
	public Optional<Boolean> updatePermissions(
			Long id, List<IPermission> permission
	) {
		Optional<IRole> optional = this._findById( id );
		return optional.map( role -> {
			Set<Permission> perm = permission.stream().map( permEntity -> ( Permission ) permEntity ).collect( Collectors.toSet() );
			role.setPermissions( perm );
			this.dataRepository.save( ( Role ) role );
			return of( true );
		} ).orElseGet( () -> of( false ) );
	}

	private Optional<IRole> _findById( Long id ) {
		Optional<Role> optional = dataRepository.findById( id );
		return optional.map( role -> role );
	}
}
