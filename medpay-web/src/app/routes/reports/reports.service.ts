import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '@env/environment';
import { ReportsSearchPayload } from './_data/reports.payload';

@Injectable({providedIn: 'root'})
export class ReportsService{
    private url: string = environment.api.baseUrl + '/report-transaction';

    constructor(private http: HttpClient) {
    }

    public getDailyCollectionReport(searchPayload: ReportsSearchPayload){
        return this.http.post(`${this.url}/daily-collection-report`, searchPayload, {responseType: 'arraybuffer'});
    }

    public getAgentCollectionReport(searchPayload: ReportsSearchPayload){
        return this.http.post(`${this.url}/agent-collection-report`, searchPayload, {responseType: 'arraybuffer'});
    }

}