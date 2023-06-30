import { Component, OnDestroy, OnInit } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd/message';
import { AuthorizationService } from '../../authorization.service';
import { Subscription } from 'rxjs';
import { PermissionListPayload, PermissionPayload, RolePayload } from '../../_data/authorization.payload';
import { displayError, splitEnum } from '@shared';
import { ToastrService } from 'ngx-toastr';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
    selector: 'app-authorization-permission',
    templateUrl: './permission.component.html',
    styles: [
        `.p-b-10px {
            padding-bottom: 12px;
        }`,
    ],
})
export class AuthorizationPermissionComponent implements OnInit, OnDestroy {

    public data: PermissionListPayload[] = [];
    public isFetchingData = false;
    public searchPayload?: RolePayload;
    public isLoading = false;

    private sub = new Subscription();
    private selectedPermissions: PermissionPayload[] = [];

    constructor(
        private msg: NzMessageService,
        private service: AuthorizationService,
        private toaster: ToastrService,
        private spinner: NgxSpinnerService,
    ) {
    }

    ngOnInit(): void {
        if (this.data.length < 1) {
            this.loadPermissionsList();
        }
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    public get count(): number {
        return this.selectedPermissions.length;
    }

    public roleSelected(role: RolePayload) {
        if (role && role.id) {
            this.searchPayload = role;
            this.spinner.show().then();
            this.sub.add(
                this.service.getRolePermissions(role.id).subscribe({
                    next: (res) => {
                        this.spinner.hide().then();
                        if (res) {
                            this.selectedPermissions = res;
                        }
                    },
                    error: (err) => {
                        this.spinner.hide().then();
                        displayError(err, { toastService: this.toaster });
                    },
                }),
            );
        }

    }

    public isSelected(permission: PermissionPayload): boolean {
        return this.findIndex(permission) > -1;
    }

    public format(value?: any) {
        return splitEnum(value ?? '');
    }

    public saveRole() {
        if (!this.selectedPermissions.length) {
            this.toaster.error('Select at least 1 permission');
            return;
        }
        if (!this.searchPayload?.id) {
            this.toaster.error('Select a role');
            return;
        }
        this.isLoading = true;
        this.sub.add(
            this.service.updateRolePermissions(this.searchPayload?.id, this.selectedPermissions).subscribe({
                next: (res) => {
                    this.isLoading = false;
                    if (res) {
                        this.toaster.success('Successful');
                    }
                },
                error: (err) => {
                    this.isLoading = false;
                    displayError(err, { toastService: this.toaster });
                },
            }),
        );

    };

    public onResetSearchPayload = () => {
        this.searchPayload = undefined;
        this.msg.success('Reset, Done!');
    };

    public onCancelAction = () => {
        this.msg.info('Action Cancelled');
    };

    public onSearch = () => {
    };

    public checkChanged(checked: boolean, permission: PermissionPayload) {
        if (!this.searchPayload || !this.searchPayload.id) {
            this.toaster.error('Select a role first');
            return;
        }
        this.addOrRemoveFromSelected(checked, permission);
    }

    private addOrRemoveFromSelected(checked: boolean, permission: PermissionPayload) {
        const index = this.findIndex(permission);
        if (checked) {
            this.selectedPermissions.push(permission);
            return;
        } else {
            if (index > -1) {
                this.selectedPermissions.splice(index, 1);
            }
        }
    }

    private findIndex(permission: PermissionPayload): number {
        return this.selectedPermissions.findIndex(value => value.id === permission.id, 0);
    }

    private loadPermissionsList() {
        this.isFetchingData = true;
        this.sub.add(
            this.service.getPermissions().subscribe({
                next: (res) => {
                    this.data = res;
                    this.isFetchingData = false;
                },
                error: (err) => {
                    this.isFetchingData = false;
                    displayError(err, { toastService: this.toaster });
                },
            }),
        );
    }
}
