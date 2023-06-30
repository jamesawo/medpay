import { Component, OnDestroy, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd/message';
import { ToastrService } from 'ngx-toastr';
import { ServiceGroupService } from '../../service-group.service';
import { ServiceGroupPayload, ServiceGroupSearchPayload } from '../../_data/service-group.payload';
import { HospitalPayload } from '../../../hospital/data/hospital.payload';
import { Subscription } from 'rxjs';
import { HospitalSearchComponent } from '../../../hospital/components/hospital-search/hospital-search.component';
import { IDateRange } from '../../../../shared/types/shared.interface';
import { displayError } from '@shared';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';

@Component({
    selector: 'app-service-group-manage',
    templateUrl: './service-group-manage.component.html',
    styles: [],
})
export class ServiceGroupManageComponent implements OnInit, OnDestroy {
    @ViewChild('hospitalSearchComponent')
    private hospitalSearchComponent: HospitalSearchComponent | undefined;

    public isExpanded = false;
    public searchPayload: ServiceGroupSearchPayload = new ServiceGroupSearchPayload();
    public tableData: ServiceGroupPayload[] = [];
    public isLoading: boolean = false;
    public isUpdating: boolean = false;
    public allInCurrentPageChecked = false;
    public indeterminate = false;
    public listOfData: readonly any[] = [];
    public listOfCurrentPageData: readonly any[] = [];
    public setOfCheckedId = new Set<number>();

    private sub = new Subscription();

    constructor(
        private msg: NzMessageService,
        private toaster: ToastrService,
        private service: ServiceGroupService,
        private modal: NzModalService,
    ) {
    }

    ngOnInit(): void {
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    public onHospitalSelected(hospital: HospitalPayload): void {
        this.searchPayload.hospital = hospital;
    }

    public onSearch = () => {
        if (!this.searchPayload.hospital || !this.searchPayload.hospital.id) {
            this.toaster.error('Please select a hospital first.', 'Validation Error');
            return;
        }
        this.isLoading = true;
        this.sub.add(
            this.service.getServiceGroupByHospital(this.searchPayload.hospital.id).subscribe({
                next: (res) => {
                    this.isLoading = false;
                    this.tableData = res;
                },

                error: (err) => {
                    this.isLoading = false;
                    displayError(err, { toastService: this.toaster });
                },
            }),
        );

    };

    public onResetSearchPayload = () => {
        this.hospitalSearchComponent?.onClear();
        this.searchPayload = new ServiceGroupSearchPayload();
        this.tableData = [];
        this.msg.success('Reset, Done!');
    };

    public onCancelAction = () => {
        this.msg.info('Action Cancelled');
    };

    public onDateRangeSelected(dateRange: IDateRange) {
        this.searchPayload.dateRange = dateRange;
    }

    public onSwitchChange(value: boolean) {
        this.searchPayload.isEnabled = value;
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

    public onAllChecked(checked: boolean): void {
        this.listOfCurrentPageData
            .filter(({ checked }) => !checked)
            .forEach(({ id }) => this.updateCheckedSet(checked, id));
        this.refreshCheckedStatus();
    }

    public updateServiceGroup(ref: NzModalRef) {
        const data: ServiceGroupPayload = ref.getConfig().nzComponentParams as ServiceGroupPayload;
        if (!data.title) {
            this.toaster.error('Enter service group title', 'Validation Error');
            return;
        }
        this.isUpdating = true;
        this.sub.add(
            this.service.updateServiceGroup(data).subscribe(
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

    private modifyTableDataAfterUpdateSuccessful(data: ServiceGroupPayload): void {
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
}
