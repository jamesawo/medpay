export enum UserTypeEnum {
    // SUPER_USER = 'SUPER_USER',
    ADMIN_USER = 'ADMIN_USER',
    AGENT_USER = 'AGENT_USER',
    REGULAR_USER = 'REGULAR_USER'
}

export enum UserPreferredNameEnum {
    FIRST_NAME = 'FIRST_NAME',
    LAST_NAME = 'LAST_NAME',
    OTHER_NAME = 'OTHER_NAME',
    ANY_NAME = 'ANY_NAME',
}

export enum UserGenderEnum {
    MALE = 'MALE',
    FEMALE = 'FEMALE',
    OTHER = 'OTHER'
}

export enum  UserLimitTypeEnum {
    TRANSACTION_VOLUME = 'TRANSACTION_VOLUME',
    TRANSACTION_AMOUNT = 'TRANSACTION_AMOUNT',
    PERFORMANCE = 'PERFORMANCE'
}