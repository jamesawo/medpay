

export class HospitalBillTotalPayload {
    netAmount?: string;
    discountAmount?: string;
    grossAmount?: string;
}

export class HospitalBillPatientPayload {
    phoneNumber?: string;
    fullName?: string;
    patientNumber?: string;
}

export class HospitalBillItemDetailPayload {
    quantity?: string;
    description?: string;
    amount?: string;
}

export class HospitalBillCashierDetailPayload {
    name?: string;
    location?: string;
}

export  class HospitalBillPayload {
    date?: string;
    billNumber?: string;
    hasTotal = false;
    hasPatient = false;
    hasItems = false;
    hasCashier = false;
    total?: HospitalBillTotalPayload;
    patient?: HospitalBillPatientPayload ;
    items: HospitalBillItemDetailPayload[]  = [];
    cashier?: HospitalBillCashierDetailPayload;
}