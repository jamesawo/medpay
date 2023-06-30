import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '@env/environment';
import { UserBasicDetailsPayload, UserPayload, UserSearchPayload } from './_data/user.payload';

@Injectable({providedIn: "root"})
export class UsersService {

    private mainUrl: string = environment.api.baseUrl + '/user';
    private addressUrl: string = environment.api.baseUrl + '/user-address';
    private detailsUrl: string = environment.api.baseUrl + '/user-details';
    private configUrl: string = environment.api.baseUrl + '/user-configuration';

    constructor(private http: HttpClient) {
    }

    public createUser(user: UserPayload) {
        return this.http.post<UserPayload>(`${this.mainUrl}/create`, user);
    }

    public updateUserBasicDetails(id?: number, payload?: UserBasicDetailsPayload) {
        return this.http.put<UserBasicDetailsPayload>(`${this.detailsUrl}/${id}`, payload);
    }

    public searchWithSearchPayload(searchPayload: UserSearchPayload) {
        return this.http.post<UserPayload[]>(`${this.mainUrl}/search-by-request`, searchPayload);
    }

    public updateAddressAndLimitType(userPayload: UserPayload){
        return this.http.put<UserPayload>(`${this.mainUrl}/update/address-limit`, userPayload);
    }

    public searchByTerm(value: string) {
        return this.http.get<UserPayload[]>(`${this.mainUrl}/search-by-term?term=${value}`);
    }

}
