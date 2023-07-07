package com.james.medpay.features.users.data.interactor.implementation;

import com.james.medpay.core.annotation.Interactor;
import com.james.medpay.core.params.EmptyParam;
import com.james.medpay.core.params.Param;
import com.james.medpay.features.authentication.data.request.RegisterRequest;
import com.james.medpay.features.users.data.interactor.contract.IUserEInteractor;
import com.james.medpay.features.users.data.request.UserRequest;
import com.james.medpay.features.users.data.request.UserSearchRequest;
import com.james.medpay.features.users.data.request.UserSearchSpecification;
import com.james.medpay.features.users.domain.entity.UserEntity;
import com.james.medpay.features.users.domain.entity.contract.IUserEntity;
import com.james.medpay.features.users.domain.entity.enums.UserTypeEnum;
import com.james.medpay.features.users.domain.usecase.usersBaseUsecase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.james.medpay.features.users.domain.entity.enums.TrimPatternEnum.FULL;
import static org.springframework.http.ResponseEntity.ok;

@Interactor
@RequiredArgsConstructor
public class UserEInteractor implements IUserEInteractor {

	private final UserCreateUsecase createUsecase;
	private final UserGetAllUsecase getAllUsecase;
	private final UserGetByIdUsecase getByIdUsecase;
	private final UserUpdateExpiryDateUsecase updateExpiryDateUsecase;
	private final UserUpdateStatusUsecase updateStatusUsecase;
	private final UserUpdateTypeDateUsecase updateTypeDateUsecase;
	private final UserGetBySearchRequestUsecase searchRequestUsecase;
	private final UserUpdateAddressAndLimitUsecase updateAddressAndLimitUsecase;
	private final UserGetBySearchTermUsecase bySearchTermUsecase;


	public IUserEntity register(UserRequest request){
		return this.createUsecase.execute(toEntity(request) );
	}

	@Override
	public ResponseEntity<UserRequest> createUser( UserRequest user ) {
		UserEntity result = ( UserEntity ) this.createUsecase.execute( toEntity( user ) );
		result.trim( FULL );
		return ok().body( toRequest( result ) );
	}

	@Override
	public ResponseEntity<Optional<UserRequest>> getUserById( Long id, boolean trim ) {
		Optional<IUserEntity> optional = this.getByIdUsecase.execute( id );
		return ok().body( optional.map( user -> _trimUserEntityToUserRequest( user, trim ) ) );
	}

	private UserRequest _trimUserEntityToUserRequest( IUserEntity userEntity, boolean trim ) {
		UserEntity user = ( UserEntity ) userEntity;
		if ( trim ) user.trim( FULL );
		return toRequest( user );
	}

	@Override
	public ResponseEntity<Optional<Boolean>> updateUserIsEnabledStatusById( Long id, boolean status ) {
		Optional<Boolean> optional = this.updateStatusUsecase.execute( new Param<>( id, status ) );
		return ok().body( optional );
	}

	@Override
	public ResponseEntity<Optional<Boolean>> updateUserExpiryDateById( Long id, LocalDate date ) {
		Optional<Boolean> optional = this.updateExpiryDateUsecase.execute( new Param<>( id, date ) );
		return ok().body( optional );
	}

	@Override
	public ResponseEntity<Optional<Boolean>> updateUserTypeById( Long id, UserTypeEnum userType ) {
		Optional<Boolean> optional = this.updateTypeDateUsecase.execute( new Param<>( id, userType ) );
		return ok().body( optional );
	}

	@Override
	public ResponseEntity<List<UserRequest>> getAllUsers( boolean trim ) {
		List<IUserEntity> users = getAllUsecase.execute( new EmptyParam() );
		return _getAllToUserRequestList( users, trim );
	}

	private ResponseEntity<List<UserRequest>> _getAllToUserRequestList( List<IUserEntity> list, boolean trim ) {
		if ( !list.isEmpty() ) {
			List<UserRequest> requests = list.stream().filter( IUserEntity::isNotSuperUser )
					.map( userEntity -> _trimUserEntityToUserRequest( userEntity, trim ) )
					                         .collect( Collectors.toList() );
			return ok().body( requests );
		}
		return ok().body( new ArrayList<>() );
	}

	@Override
	public ResponseEntity<List<UserRequest>> searchByRequestCriteria( UserSearchRequest request ) {
		List<IUserEntity> result = searchRequestUsecase.execute( UserSearchSpecification.byCriteria( request ) );
		return ok().body(_filterThenMapToUserRequest(result));
	}

	private List<UserRequest> _filterThenMapToUserRequest(List<IUserEntity> list) {
		return list
				.stream()
				.filter( IUserEntity::isNotSuperUser )
				.map(UserEInteractor::toRequest)
				.collect( Collectors.toList() );
	}

	@Override
	public ResponseEntity<Optional<UserRequest>> updateAddressAndLimit( UserRequest request ) {
		Optional<IUserEntity> optional = this.updateAddressAndLimitUsecase.execute( toEntity( request ) );
		return ok( optional.map( UserEInteractor::toRequest ));
	}

	@Override
	public ResponseEntity<List<UserRequest>> searchByTerm( String term ){
		List< IUserEntity > list = this.bySearchTermUsecase.execute(UserSearchSpecification.byCriteria(term));
		return ok().body(_filterThenMapToUserRequest(list));
	}

	private static UserEntity toEntity( UserRequest userRequest){
		return new UserRequest().toEntity( userRequest );
	}

	private static UserRequest toRequest( IUserEntity user){
		return new UserRequest().toRequest( ( UserEntity ) user );
	}

}
