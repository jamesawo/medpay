import { Component, OnDestroy, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd/message';
import { ToastrService } from 'ngx-toastr';
import { ServiceGroupService } from '../../service-group.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { HospitalPayload } from '../../../hospital/data/hospital.payload';
import { displayError, splitEnum } from '@shared';
import { RevenueHeadPayload, RevenueHeadSearchPayload, ServiceGroupPayload } from '../../_data/service-group.payload';
import { Subscription } from 'rxjs';
import { HospitalSearchComponent } from '../../../hospital/components/hospital-search/hospital-search.component';
import { ServiceGroupSearchComponent } from '../../components/service-group-search/service-group-search.component';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';

@Component({
    selector: 'app-revenue-head-manage',
    templateUrl: './revenue-head-manage.component.html',
    styles: [],
})
export class RevenueHeadManageComponent implements OnInit, OnDestroy {
    @ViewChild('hospitalSearchComponent')
    public hospitalSearchComponent?: HospitalSearchComponent;
    @ViewChild('serviceGroupSearchComponent')
    public serviceGroupSearchComponent?: ServiceGroupSearchComponent;
    public tableData: RevenueHeadPayload[] = [];

    public searchPayload: RevenueHeadSearchPayload = new RevenueHeadSearchPayload();
    public isLoading: boolean = false;
    public isUpdating: boolean = false;
    public isExpanded: boolean = false;
    public serviceGroups: ServiceGroupPayload[] = [];
    public isHospitalInvalid = false;
    public isServiceGroupInvalid: boolean = false;

    public allInCurrentPageChecked = false;
    public indeterminate = false;
    public listOfData: readonly any[] = [];
    public listOfCurrentPageData: readonly any[] = [];
    public setOfCheckedId = new Set<number>();


    private sub = new Subscription();

    constructor(
        private fb: FormBuilder,
        private msg: NzMessageService,
        private toaster: ToastrService,
        private service: ServiceGroupService,
        private spinner: NgxSpinnerService,
        private nzMessageService: NzMessageService,
        private modal: NzModalService,
    ) {
    }

    ngOnInit(): void {
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    public onServiceGroupSelected(serviceGroup: ServiceGroupPayload) {
        if (serviceGroup && serviceGroup.id) {
            this.searchPayload.serviceGroup = serviceGroup;
            this.isServiceGroupInvalid = false;
        }
    }

    public onHospitalSelected(hospitalPayload: HospitalPayload) {
        if (hospitalPayload && hospitalPayload.id) {
            this.searchPayload.hospital = hospitalPayload;
            this.isHospitalInvalid = false;
            this.serviceGroupSearchComponent?.getServiceGroupByHospital(hospitalPayload.id);
        }
    }


    public onResetSearchPayload = () => {
        this.searchPayload = new RevenueHeadSearchPayload();
        this.hospitalSearchComponent?.onClear();
        this.serviceGroupSearchComponent?.onClear();
        this.tableData = [];
    };

    public onSearch = () => {
        this.isHospitalInvalid = !this.searchPayload.hospital;
        this.isServiceGroupInvalid = !this.searchPayload.serviceGroup;
        if(this.isHospitalInvalid || this.isServiceGroupInvalid) {
            this.msg.error('Invalid field entry');
            return;
        }
        this.isLoading = true;
        const servGroupId = this.searchPayload.serviceGroup?.id;
        this.sub.add(
            this.service.searchRevenueHead(servGroupId).subscribe({
                next: (res) => {
                    if (res) {
                        this.tableData = res;
                        this.isLoading = false;
                    }
                },
                error: (err) => {
                    this.isLoading = false;
                    displayError(err, { toastService: this.toaster });
                },
            }),
        );
    };

    public onCancelAction = () => {
        this.nzMessageService.info('Action Cancelled');
    };

    public onAllChecked(value: boolean) {
    }

    public updateCheckedSet(checked: boolean, id: number): void {
        if (checked) {
            this.setOfCheckedId.add(id ?? 0);
        } else {
            this.setOfCheckedId.delete(id ?? 0);
        }
    }

    public onCurrentPageDataChange(listOfCurrentPageData: readonly any[]): void {
        this.listOfCurrentPageData = listOfCurrentPageData;
        this.refreshCheckedStatus();
    }

    public refreshCheckedStatus(): void {
        const listOfEnabledData = this.listOfCurrentPageData.filter(({ checked }) => !checked);
        this.allInCurrentPageChecked = listOfEnabledData.every(({ id }) => this.setOfCheckedId.has(id));
        this.indeterminate = listOfEnabledData.some(({ id }) => this.setOfCheckedId.has(id)) && !this.allInCurrentPageChecked;
    }

    public onItemChecked(checked: boolean, id?: number): void {
        this.updateCheckedSet(checked, id ?? 0);
        this.refreshCheckedStatus();
    }

    public updateRevenueHead(ref: NzModalRef) {
        const data: RevenueHeadPayload = ref.getConfig().nzComponentParams as RevenueHeadPayload;
        if (!data.title) {
            this.toaster.error('Enter title', 'Validation Error');
            return;
        }
        this.isUpdating = true;
        this.sub.add(
            this.service.updateRevenueHead(data).subscribe(
                {
                    next: (res) => {
                        this.isUpdating = false;
                        if (res) {
                            this.toaster.success('Updated Successfully');
                            this.modifyTableDataAfterUpdateSuccessful(res);
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

    private modifyTableDataAfterUpdateSuccessful(data: RevenueHeadPayload): void {
        let index = this.tableData.findIndex(value => value.id === data.id);
        if (index >= 0) {
            this.tableData[index] = data;
        }
    }

    public createTplModal(tplTitle: TemplateRef<{}>, tplContent: TemplateRef<{}>, tplFooter: TemplateRef<{}>, data: ServiceGroupPayload): void {
        this.modal.create({
            nzTitle: tplTitle,
            nzContent: tplContent,
            nzFooter: tplFooter,
            nzMaskClosable: false,
            nzClosable: false,
            nzComponentParams: Object.assign({}, data),
        });
    }

    public getCategory(category?: string): string {
        return splitEnum(category ?? '');
    }
}
