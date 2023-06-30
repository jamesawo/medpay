export enum HospitalEnum {

}

export enum HospitalCollectionEnum {
    MANUAL = 'MANUAL',
    GATEWAY = 'GATEWAY'
}

export enum HospitalSupportChannelEnum {
    LIVE_CHAT = 'LIVE_CHAT',
    TICKET = 'TICKET',
    EMAIL = 'EMAIL',
    FAQ = 'FAQ',
}

export enum HospitalAuthTypeEnum {
    HTTP_BASIC_AUTH= 'HTTP_BASIC_AUTH',
    API_KEY_AUTH= 'API_KEY_AUTH',
    OAUTH_AUTH= 'OAUTH_AUTH',
    NO_AUTH = 'NO_AUTH'
}

export  enum HospitalEnvironmentEnum {
    LIVE = 'LIVE',
    TEST = 'TEST'
}

export enum HospitalSettlementChargeEnum {
    EXTRA_CHARGE = 'EXTRA_CHARGE',
    EMBEDDED_CHARGE = 'EMBEDDED_CHARGE'
}