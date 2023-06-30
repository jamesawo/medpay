import { UserGenderEnum, UserLimitTypeEnum, UserPreferredNameEnum, UserTypeEnum } from './user.enum';
import { HospitalPayload } from '../../hospital/data/hospital.payload';
import { IDateRange } from '../../../shared/types/shared.interface';
import { RolePayload } from '../../authorization/_data/authorization.payload';

export class UserAddressPayload {
    id?: number;
    state: string = '';
    city: string = '';
    street: string = '';
    currentPlaceOfResidence: string = '';
    isVerified: boolean = false

}

export class UserBasicDetailsPayload {
    id: string = '';
    phoneNumber: string = '';
    firstName: string = '';
    lastName: string = '';
    otherName: string = '';
    gender?: UserGenderEnum;
    preferredPronoun: string = '';
    preferredName?: UserPreferredNameEnum;
    socialFb: string = '';
    socialIg: string = '';
    socialLkd: string = '';
    personalStatement: string = '';
    emergencyPhone: string = '';
}

export class UserConfigurationPayload {
    id?: number;
    isEnabled: boolean = true;
    maxLimit: number = 10000;
    minLimit: number = 1000;
    limitType?: UserLimitTypeEnum;
}

export class UserPayload {
    id? : number;
    nickName: string = '';
    uuid?: string;
    email: string = '';
    password: string = '';
    isEnabled: boolean = true;
    expiryDate: string = '';
    userTypeEnum?: UserTypeEnum;
    address: UserAddressPayload = new UserAddressPayload();
    basicDetails: UserBasicDetailsPayload = new UserBasicDetailsPayload();
    emergencyAddress: UserAddressPayload = new UserAddressPayload();
    configuration: UserConfigurationPayload = new UserConfigurationPayload();
    hospital: HospitalPayload = new HospitalPayload();
    disabled: boolean = false;
    createdAt: string = '';
    roles?: RolePayload[];

    constructor(id?: number) {
        this.id = id;
    }
}

export class UserSearchPayload {
    name: string = '';
    hospital?: HospitalPayload;
    phoneNumber: string = '';
    email: string = '';
    userTypeEnum?: UserTypeEnum;
    genderEnum?: UserGenderEnum;
    isEnabled?: boolean;
    dateRange?: IDateRange;
}