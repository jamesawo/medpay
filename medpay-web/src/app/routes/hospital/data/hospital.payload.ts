import {
    HospitalAuthTypeEnum,
    HospitalCollectionEnum,
    HospitalEnvironmentEnum,
    HospitalSettlementChargeEnum,
    HospitalSupportChannelEnum,
} from './hospital.enum';
import {IDateRange} from '../../../shared/types/shared.interface';


export class HospitalBasicDetailPayload {
    id: number | undefined;
    name: string | undefined;
    hospitalLogoUrl: string | undefined;
    supportName: string | undefined;
    supportPhone: string | undefined;
    supportEmail: string | undefined;
    hospitalAddress: string | undefined;
    supportChannel: HospitalSupportChannelEnum | undefined;

    public validate(): { messages: string[], status: boolean } {
        const empty: string[] = [];
        let result = { messages: empty, status: true };
        if (!this.name) {
            result.messages.push('Hospital name is empty');
            result.status = false;
        }
        if (!this.supportName) {
            result.messages.push('Hospital support staff name is empty');
            result.status = false;
        }
        if (!this.supportEmail) {
            result.messages.push('Hospital support staff email is empty');
            result.status = false;
        }
        if (!this.supportPhone) {
            result.messages.push('Hospital support staff phone is empty');
            result.status = false;
        }
        if (!this.hospitalAddress) {
            result.messages.push('Hospital address is empty');
            result.status = false;
        }
        if (!this.supportChannel) {
            result.messages.push('Hospital primary support channel is empty');
            result.status = false;
        }
        return result;
    }
}

// todo :: replace HospitalServiceGroupPayload with ServiceGroupPayload in ServiceGroup module payload
export class HospitalServiceGroupPayload {
    id: number | undefined;
    serviceGroupCode: string | undefined;
    serviceGroupTitle: string | undefined;
    serviceGroupIsEnabled: boolean | undefined;

    public validate(): { messages: string[], status: boolean } {
        let result = { messages: [''], status: true };

        if (!this.serviceGroupTitle) {
            result.messages.push('Hospital service group title is empty');
            result.status = false;
        }
        return result;
    }
}

export class HospitalApiConfigurationPayload {
    id: number | undefined;
    apiBaseUrl: string | undefined;
    apiTestBaseUrl: string | undefined;
    authenticationType: HospitalAuthTypeEnum | undefined;
    publicToken: string | undefined;
    privateToken: string | undefined;
    apiKey: string | undefined;
    oauthToken: string | undefined;
    environmentEnum: HospitalEnvironmentEnum | undefined;
    searchBillMethod: string | undefined;
    payBillMethod: string | undefined;
    searchBillEndpoint: string | undefined;
    payBillEndpoint: string | undefined;

    public validate(): { messages: string[], status: boolean } {
        const empty: string[] = [];
        let result = { messages: empty, status: true };

        if (!this.apiBaseUrl) {
            result.messages.push('Api live base url is empty');
            result.status = false;
        }

        if (!this.apiTestBaseUrl) {
            result.messages.push('Api test base url is empty');
            result.status = false;
        }

        if (!this.authenticationType) {
            result.messages.push('Api authentication type is empty');
            result.status = false;
        }

        if (!this.environmentEnum) {
            result.messages.push('Api active environment is empty');
            result.status = false;
        }
        return result;
    }
}

export class HospitalSettlementConfigurationPayload {
    settlementId: number | undefined;
    settlementChargeEnum: HospitalSettlementChargeEnum | undefined;
}

export class HospitalPayload {
    id?: number;
    isEnabled?: boolean;
    expiryDate: string = '';
    hasHospitalSoftware: boolean = false;
    collectionModelEnum?: HospitalCollectionEnum;
    detail: HospitalBasicDetailPayload;
    startDate: string | undefined;
    serviceGroup: HospitalServiceGroupPayload;
    apiConfiguration: HospitalApiConfigurationPayload;
    settlementConfiguration: HospitalSettlementConfigurationPayload;
    checked: boolean = false;
    disabled: boolean = false;

    constructor(id?: number) {
        this.id = id;
        this.detail = new HospitalBasicDetailPayload();
        this.serviceGroup = new HospitalServiceGroupPayload();
        this.apiConfiguration = new HospitalApiConfigurationPayload();
        this.settlementConfiguration = new HospitalSettlementConfigurationPayload();
    }

    public validate(): { messages: string[], status: boolean } {
        const empty: string[] = [];
        let result = { messages: empty, status: true };
        if (!this.expiryDate) {
            result.messages.push('Expiry date is empty');
            result.status = false;
        }
        if (!this.collectionModelEnum) {
            result.messages.push('Collection mode is empty');
            result.status = false;
        }

        if (this.detail) {
            let validate = this.detail.validate();
            result.messages.push(...validate.messages);
            result.status = validate.status;
        } else {
            result.messages.push('Basic details is empty');
            result.status = false;
        }

        if (this.hasHospitalSoftware) {
            if (this.apiConfiguration) {
                let validate = this.apiConfiguration.validate();
                result.messages.push(...validate.messages);
                result.status = validate.status;
            } else {
                result.messages.push('Enter api configuration details or turn off hasSoftware');
                result.status = false;
            }
        }

        return result;
    }

}

export class HospitalSearchPayload {
    hospitalName: string | undefined;
    supportName: string | undefined;
    channelEnum: HospitalSupportChannelEnum | undefined;
    dateRange: IDateRange | undefined;
    isEnabled: boolean = true;
    hasHospitalSoftware: boolean = true;
    environmentEnum: HospitalEnvironmentEnum | undefined;
    collectionModelEnum: HospitalCollectionEnum | undefined;
}

export class HospitalPartialPayload {
    collectionMode?: HospitalCollectionEnum;
    expiryDate?: string;
    isEnabled?: boolean;
    hospitalHasSoftware?: boolean;
}