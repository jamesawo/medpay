import { Injectable } from '@angular/core';
import {
    HospitalApiConfigurationPayload,
    HospitalBasicDetailPayload,
    HospitalPartialPayload,
    HospitalPayload,
    HospitalSearchPayload,
} from './data/hospital.payload';
import { environment } from '@env/environment';
import { HttpClient } from '@angular/common/http';
import { HospitalBillPayload } from './data/hospital-bill.payload';

@Injectable({providedIn: 'root'})
export class HospitalService {
    private hospitalUrl: string = environment.api.baseUrl + '/hospital';
    private hospitalApiUrl: string = environment.api.baseUrl + '/hospital-api';
    private hospitalApiUsageUrl: string = environment.api.baseUrl + '/hospital-api-usage';

    constructor(private http: HttpClient) {
    }

    onCreateHospital(payload: HospitalPayload) {
        return this.http.post<HospitalPayload>(`${this.hospitalUrl}/create`, payload);
    }

    onSearchByPayload(payload: HospitalSearchPayload){
        return this.http.post<HospitalPayload[]>(`${this.hospitalUrl}/search-by-payload?trim=false`, payload);
    }

    onUpdateBasicDetails(hospitalId: number, payload: HospitalBasicDetailPayload){
        return this.http.put<HospitalBasicDetailPayload>(
            `${this.hospitalUrl}/update/${hospitalId}`, payload);
    }

    updateApiConfiguration(hospitalId: number, payload: HospitalApiConfigurationPayload){
        return this.http.put<HospitalApiConfigurationPayload>(
            `${this.hospitalApiUrl}/update/${hospitalId}`, payload);
    }

    updateHospitalPartialProps(hospitalId?: number, payload?: HospitalPartialPayload){
        return this.http.put<HospitalPartialPayload>(`${this.hospitalUrl}/update/collection-mode/${hospitalId}`, payload);
    }

    onSearchBillDetails(payload: { bill?: string; hospitalId?: number }) {
        return this.http.get<HospitalBillPayload>(`${this.hospitalApiUsageUrl}/bill?billNumber=${payload.bill}&hospitalId=${payload.hospitalId}`);
    }

    onSendBillPayment(){

    }

}
