export class PatientPayload {
    id?:number;
    firstName?: string;
    lastName?: string;
    otherName?: string;
    dateOfBirth?: string;
    phone?: string;
    address?: string;
    gender?: string;
    registeredAt?: string;
    patientNumber?: number;
    patientNumberPrefix?: string;
    uniqueCode?: string;

    get fullName(){
        return `${this.firstName} ${this.lastName} ${this.otherName}`;
    }
}