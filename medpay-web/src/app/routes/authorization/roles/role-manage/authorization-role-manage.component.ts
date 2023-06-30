import { Component, OnDestroy, OnInit, TemplateRef } from '@angular/core';
import { Subscription } from 'rxjs';
import { RolePayload, RoleSearchPayload } from '../../_data/authorization.payload';
import { NzMessageService } from 'ng-zorro-antd/message';
import { ToastrService } from 'ngx-toastr';
import { AuthorizationService } from '../../authorization.service';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';
import { displayError } from '@shared';

@Component({
    selector: 'app-role-manage',
    templateUrl: './authorization-role-manage.component.html',
    styles: [],
})
export class AuthorizationRoleManageComponent implements OnInit, OnDestroy {
    public searchPayload = new RoleSearchPayload();
    public tableData: RolePayload[] = [];

    public isLoadingSearchResult = false;
    public isUpdating = false;

    private sub = new Subscription();

    constructor(
        private msg: NzMessageService,
        private toaster: ToastrService,
        private service: AuthorizationService,
        private modal: NzModalService,
    ) {
    }

    ngOnInit(): void {
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    public onResetSearchPayload = () => {
        this.searchPayload = new RoleSearchPayload();
        this.tableData = [];
        this.msg.success('Reset, Done!');
    };

    public onCancelAction = () => {
        this.msg.info('Action Cancelled');
    };

    public onSearchResult = (): void => {
        this.isLoadingSearchResult = true;

        this.sub.add(
            this.service.searchRoleByTitleOrGetAll(this.searchPayload).subscribe({
                next: (res) => {
                    this.tableData = res;
                    this.msg.success('Success');
                },
                error: (error) => {
                    this.isLoadingSearchResult = false;
                    this.msg.error('Error');
                },
                complete: () => {
                    this.isLoadingSearchResult = false;
                },
            }),
        );

    };

    public updateRole(ref: NzModalRef) {
        const data: RolePayload = ref.getConfig().nzComponentParams as RolePayload;
        if (!data.name) {
            this.toaster.error('Enter title', 'Validation Error');
            return;
        }
        this.isUpdating = true;
        this.sub.add(
            this.service.updateRole(data).subscribe(
                {
                    next: (res) => {
                        this.isUpdating = false;
                        if (res) {
                            this.toaster.success('Updated Successfully');
                            this.modifyTableDataAfterUpdateSuccessful(data);
                            ref.destroy();
                        }
                    },
                    error: (err) => {
                        this.isUpdating = false;
                        displayError(err, { toastService: this.toaster });
                    },
                }),
        );
    }

    private modifyTableDataAfterUpdateSuccessful(data: RolePayload): void {
        let index = this.tableData.findIndex(value => value.id === data.id);
        if (index >= 0) {
            this.tableData[index] = data;
        }
    }

    public createTplModal(tplTitle: TemplateRef<{}>, tplContent: TemplateRef<{}>, tplFooter: TemplateRef<{}>, data: RolePayload): void {
        this.modal.create({
            nzTitle: tplTitle,
            nzContent: tplContent,
            nzFooter: tplFooter,
            nzMaskClosable: false,
            nzClosable: false,
            nzComponentParams: Object.assign({}, data),
        });
    }

}
