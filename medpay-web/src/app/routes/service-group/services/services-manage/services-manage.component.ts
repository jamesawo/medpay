import { Component, OnDestroy, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { HospitalSearchComponent } from '../../../hospital/components/hospital-search/hospital-search.component';
import { ServiceGroupSearchComponent } from '../../components/service-group-search/service-group-search.component';
import { RevenueHeadPayload, ServiceGroupPayload, ServicePayload } from '../../_data/service-group.payload';
import { Subscription } from 'rxjs';
import { FormBuilder } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd/message';
import { ToastrService } from 'ngx-toastr';
import { ServiceGroupService } from '../../service-group.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';
import { HospitalPayload } from '../../../hospital/data/hospital.payload';
import { displayError } from '@shared';
import { RevenueHeadSearchComponent } from '../../components/revenue-head-search/revenue-head-search.component';

@Component({
    selector: 'app-services-manage',
    templateUrl: './services-manage.component.html',
    styles: [],
})
export class ServicesManageComponent implements OnInit, OnDestroy {
    @ViewChild('hospitalSearchComponent')
    public hospitalSearchComponent?: HospitalSearchComponent;
    @ViewChild('serviceGroupSearchComponent')
    public serviceGroupSearchComponent?: ServiceGroupSearchComponent;
    @ViewChild('revenueHeadSearchComponent')
    public revenueHeadSearchComponent?: RevenueHeadSearchComponent;


    public tableData: ServicePayload[] = [];
    public serviceGroups: ServiceGroupPayload[] = [];
    public revenueHeads: RevenueHeadPayload[] = [];

    public searchPayload: ServicePayload = new ServicePayload();
    public isLoading: boolean = false;
    public isUpdating: boolean = false;
    public isExpanded: boolean = false;
    public isHospitalInvalid: boolean = false;
    public isServiceGroupInvalid: boolean = false;
    public isRevenueHeadSelectInvalid: boolean = false;


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

    public onServiceGroupSelected(serviceGroup: ServiceGroupPayload): void {
        if (serviceGroup && serviceGroup.id) {
            this.isServiceGroupInvalid = false;
            this.revenueHeadSearchComponent?.getRevenueHeadsByServiceGroup(serviceGroup.id);
        }
    }

    public onHospitalSelected(hospitalPayload: HospitalPayload): void {
        if (hospitalPayload && hospitalPayload.id) {
            this.isHospitalInvalid = false;
            this.serviceGroupSearchComponent?.getServiceGroupByHospital(hospitalPayload.id);
        }
    }

    public onRevenueHeadSelected(revenueHeadPayload: RevenueHeadPayload): void {
        if (revenueHeadPayload && revenueHeadPayload.id) {
            this.isRevenueHeadSelectInvalid = false;
            this.searchPayload.revenueHead = revenueHeadPayload;
        }
    }

    public onResetSearchPayload = () => {
        this.searchPayload = new ServicePayload();
        this.hospitalSearchComponent?.onClear();
        this.serviceGroupSearchComponent?.onClear();
        this.revenueHeadSearchComponent?.onClear();
        this.tableData = [];
    };

    public onSearch = () => {
        if (!this.searchPayload.revenueHead || !this.searchPayload.revenueHead?.id) {
            this.msg.error('Invalid field entry');
            return;
        }

        this.isLoading = true;
        this.sub.add(
            this.service.getAllServiceByRevenueHead(this.searchPayload.revenueHead?.id).subscribe({
                next: (res) => {
                    if (res) {
                        this.tableData = res;
                        this.isLoading = false;
                        this.msg.success('Successful');
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

    public updateService(ref: NzModalRef) {
        const data: ServicePayload = ref.getConfig().nzComponentParams as ServicePayload;
        if (!data.title) {
            this.toaster.error('Enter title', 'Validation Error');
            return;
        }
        this.isUpdating = true;
        this.sub.add(
            this.service.updateService(data).subscribe(
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

    private modifyTableDataAfterUpdateSuccessful(data: ServicePayload): void {
        let index = this.tableData.findIndex(value => value.id === data.id);
        if (index >= 0) {
            this.tableData[index] = data;
        }
    }

    public createTplModal(tplTitle: TemplateRef<{}>, tplContent: TemplateRef<{}>, tplFooter: TemplateRef<{}>, data: ServicePayload): void {
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
