
package com.james.medpay.features.users.domain.entity.contract;

import com.james.medpay.features.users.domain.entity.enums.UserLimitTypeEnum;

public interface IUserConfigurationEntity {

	Long getId();

	void setId( Long id );

	Boolean getIsEnabled();

	void setIsEnabled( Boolean enable);

	Integer getMaxLimit();

	void setMaxLimit( Integer maxLimit );

	Integer getMinLimit();

	void setMinLimit( Integer minLimit );

	UserLimitTypeEnum getLimitType();

	void setLimitType( UserLimitTypeEnum limitType );
}
