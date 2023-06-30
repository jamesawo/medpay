import { HospitalPayload } from '../../hospital/data/hospital.payload';
import { IDateRange } from '../../../shared/types/shared.interface';
import { ServiceCategoryEnum } from './service-group.enum';

export class ServiceGroupPayload {
    id?: number;
    disabled: boolean = false;
    hospital?: HospitalPayload;
    title: string = '';
    isEnabled: boolean = true;
    createdAt: string = '';
}

export class ServiceGroupSearchPayload {
    hospital?: HospitalPayload;
    title?: string;
    dateRange?: IDateRange;
    isEnabled?: boolean;
}

export class RevenueHeadPayload {
    id?: number;
    title: string = '';
    isEnabled: boolean = true;
    serviceGroup?: ServiceGroupPayload;
    category?: ServiceCategoryEnum;
    createdAt: string = '';
    disabled = false;

    constructor(title?: string) {
        this.title = title ?? '';
    }
}

export class RevenueHeadSearchPayload {
    hospital?: HospitalPayload;
    serviceGroup?: ServiceGroupPayload;
}

export class ServicePayload {
    id?: number;
    title?: string;
    isEnabled: boolean = true;
    createdAt: string = '';
    revenueHead?: RevenueHeadPayload;
}