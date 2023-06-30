import { HospitalPayload } from '../../hospital/data/hospital.payload';
import { UserTypeEnum } from '../../users/_data/user.enum';

export interface LoginResponse {
    id: number;
    uuid: string;
    username: string;
    token: string;
    type: UserTypeEnum;
    fullName: string;
    email: string;
    menu: string;
    hospital: HospitalPayload;
    enabled: boolean;
    phone: string;
    profilePicUrl: string;
}