import { HospitalServiceCategoryEnum, TransactionStatusEnum } from './transaction.enum';
import { HospitalPayload } from '../../hospital/data/hospital.payload';
import { UserPayload } from '../../users/_data/user.payload';
import { HospitalService } from '../../hospital/hospital.service';
import { IDateRange } from '../../../shared/types/shared.interface';

export class TransactionPaymentDetailPayload {
    id?: number;
    billNumber?: string;
    category?: HospitalServiceCategoryEnum;
    services?: HospitalService[];
}

export class TransactionPayerDetailPayload {
    id?: number;
    fullName?: string;
    phoneNumber?: string;
    patientNumber?: string;

}

export class TransactionSettlementPayload {
    id?: number;
    split?: number;
    splitType?: string;
}

export class TransactionPayload {
    id?: number;
    uuid?: string;
    date?: string;
    reference?: string;
    token?: string;
    time?: string;
    amount?: number;
    notificationCount?: number;
    hasNotifiedHospital?: boolean;
    statusEnum?: TransactionStatusEnum;
    hospital?: HospitalPayload;
    user?: UserPayload;
    paymentDetail?: TransactionPaymentDetailPayload;
    payerDetail?: TransactionPayerDetailPayload;
    settlement?: TransactionSettlementPayload;
    receiptUrl?: string;
}

export class TransactionSearchPayload {
    hospital?: HospitalPayload;
    user?: UserPayload;
    reference?: string;
    serial?: string;
    billNumber?: string;
    status?: TransactionStatusEnum;
    dateRange?: IDateRange;
    payer?: string;
}