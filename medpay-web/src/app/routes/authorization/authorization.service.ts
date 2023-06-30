import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '@env/environment';
import {
    PermissionListPayload,
    PermissionPayload,
    ResetPasswordPayload,
    RolePayload,
    RoleSearchPayload,
} from './_data/authorization.payload';
import { Observable } from 'rxjs';

@Injectable()
export class AuthorizationService {
    private roleUrl: string = environment.api.baseUrl + '/auth-role';
    private permissionUrl: string = environment.api.baseUrl + '/auth-permission';
    private userUrl: string = environment.api.baseUrl + '/auth-user';

    constructor(private http: HttpClient) {
    }

    public createRole(payload: RolePayload) {
        return this.http.post<RolePayload>(`${this.roleUrl}/create`, payload);
    }

    public getAllRoles() {
        return this.http.get<RolePayload[]>(`${this.roleUrl}/all`);
    }

    public getRoleByTitle(value: string) {
        return this.http.get<RolePayload[]>(`${this.roleUrl}/${value}?searchBy=NAME`);
    }

    public getRoleById(roleId: number) {
        return this.http.get<RolePayload>(`${this.roleUrl}/${roleId}?searchBy=ID`);
    }

    public searchRoleByTitleOrGetAll(searchPayload: RoleSearchPayload): Observable<RolePayload[]> {
        if (searchPayload.name.length > 1) {
            return this.getRoleByTitle(searchPayload.name);
        } else {
            return this.getAllRoles();
        }
    }

    public updateRole(payload: RolePayload) {
        return this.http.put<boolean>(`${this.roleUrl}/update`, payload);
    }

    public updateRolePermissions(roleId: number, permissions: PermissionPayload[]) {
        return this.http.post<boolean>(`${this.roleUrl}/${roleId}/update-permissions`, { roleId, permissions });
    }

    public getRolePermissions(roleId: number) {
        return this.http.get<PermissionPayload[]>(`${this.roleUrl}/${roleId}/permissions`);
    }

    // permission
    public getPermissions() {
        return this.http.get<PermissionListPayload[]>(`${this.permissionUrl}/all`);
    }

    // user
    public getUserRoles(userId: number) {
        return this.http.get<RolePayload[]>(`${this.userUrl}/${userId}`);
    }

    public assignRolesToUser(userId?: number, payload?: RolePayload[]) {
        return this.http.post<boolean>(`${this.userUrl}/${userId}/assign-roles`, payload);
    }

    public resetPassword(payload: ResetPasswordPayload){
        return this.http.post<boolean>(`${this.userUrl}/reset-password`, payload);
    }
}
