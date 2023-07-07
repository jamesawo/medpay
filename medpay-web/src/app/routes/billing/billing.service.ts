import {Injectable} from "@angular/core";
import {environment} from "@env/environment";
import {PatientPayload} from "./_data/patient.payload";
import {HttpClient} from "@angular/common/http";
import {BillPayload} from "./_data/bill.payload";

@Injectable({providedIn: "root"})
export class BillingService {
    private url: string = environment.api.baseUrl + '/billing';

    constructor(private http: HttpClient) {
    }


    public registerPatient(payload: PatientPayload) {
        return this.http.post<PatientPayload>(`${this.url}/patient/register`, payload, {observe: 'response'});
    }

    public saveBill(payload: BillPayload){
        return this.http.post<BillPayload>(`${this.url}/register`, payload, {observe: "response"});
    }

}