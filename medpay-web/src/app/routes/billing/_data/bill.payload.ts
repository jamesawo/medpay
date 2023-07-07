import {HospitalPayload} from "../../hospital/data/hospital.payload";
import {PatientPayload} from "./patient.payload";
import {ServicePayload} from "../../service-group/_data/service-group.payload";
import {UserPayload} from "../../users/_data/user.payload";

export class BillPayload {
    id?: number;
    hospital?: HospitalPayload;
    patient?: PatientPayload;
    items: BillItemPayload[] = [];

    billNumber?: string;
    createdAt?: string;
    billAmount?: number;
    status?: string;
    createdBy?: UserPayload;

}

export class BillItemPayload {
    service?: ServicePayload;
    amount?: number;
}

export enum BillStatus {
    PAID = 'PAID',
    UNPAID ='UNPAID'
}