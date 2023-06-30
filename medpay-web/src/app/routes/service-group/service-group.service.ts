import { Injectable } from '@angular/core';
import { environment } from '@env/environment';
import { HttpClient } from '@angular/common/http';
import {
    RevenueHeadPayload,
    ServiceGroupPayload,
    ServiceGroupSearchPayload,
    ServicePayload,
} from './_data/service-group.payload';

@Injectable({providedIn: 'root'})
export class ServiceGroupService {

    private serviceGroupUrl: string = environment.api.baseUrl + '/hospital-service-group';
    private revenueHeadUrl: string = environment.api.baseUrl + '/hospital-revenue-head';
    private serviceUrl: string = environment.api.baseUrl + '/hospital-service';

    constructor(private http: HttpClient) {
    }

    // serviceUrl group
    public updateServiceGroup(payload: ServiceGroupPayload) {
        return this.http.post<ServiceGroupPayload>(`${this.serviceGroupUrl}/create`, payload);
    }

    public searchServiceGroup(payload: ServiceGroupSearchPayload) {
        return this.http.post<ServiceGroupPayload[]>(`${this.serviceGroupUrl}/search`, payload);
    }

    public getServiceGroupByHospital(hospitalId: number) {
        return this.http.get<ServiceGroupPayload[]>(`${this.serviceGroupUrl}/get-by-hospital/${hospitalId}`);
    }

    // revenue heads
    public createRevenueHead(groupId: number, payload: RevenueHeadPayload) {
        return this.http.post<RevenueHeadPayload>(`${this.revenueHeadUrl}/create/${groupId}`, payload);
    }

    public searchRevenueHead(serviceGroupId?: number) {
        return this.http.get<RevenueHeadPayload[]>(`${this.revenueHeadUrl}/find-in-service-group/${serviceGroupId}`);
    }

    public updateRevenueHead(payload: RevenueHeadPayload) {
        return this.http.post<RevenueHeadPayload>(`${this.revenueHeadUrl}/update`, payload);
    }

    // serviceUrl
    public createService(request: ServicePayload, revenueHeadId?: number) {
        return this.http.post<ServicePayload>(`${this.serviceUrl}/create?revenueHead=${revenueHeadId}`,request);
    }

    public updateService( payload: ServicePayload ) {
        return this.http.post<boolean>(`${this.serviceUrl}/update`, payload );
    }


    public getAllServiceByRevenueHead(revenueHeadId?: number ) {
        return this.http.get<ServicePayload[]>(`${this.serviceUrl}/get-by-revenue-head/${revenueHeadId}`);
    }


    public updateStatus( serviceId: number, status: boolean ) {
        return this.http.patch(`${this.serviceUrl}/status/${serviceId}?status=${status}`, status);
    }

    public searchServiceByTitleAndHospital(hospitalId: number, term: string) {
        return this.http.get<ServicePayload[]>(`${this.serviceUrl}/search-by-title/?title=${term}&hospital=${hospitalId}`);
    }

}
