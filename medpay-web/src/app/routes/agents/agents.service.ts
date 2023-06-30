import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '@env/environment';


@Injectable({providedIn: "root"})
export class AgentsService {
    private mainUrl: string = environment.api.baseUrl + '';

    constructor(private http: HttpClient) {
    }
}