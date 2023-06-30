package com.james.medpay.features.authentication.domain.repository.contract;

import com.james.medpay.features.authentication.domain.entity.partial.AuthLogin;

public interface IAuthLoginRepository {
	AuthLogin login( String username, String password );
}
