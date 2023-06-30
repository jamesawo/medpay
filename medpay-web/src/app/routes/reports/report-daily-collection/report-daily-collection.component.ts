import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { GroupReportEnum } from '../_data/reports.enum';
import { ReportsSearchPayload } from '../_data/reports.payload';
import { NzMessageService } from 'ng-zorro-antd/message';
import { ToastrService } from 'ngx-toastr';
import { NgxSpinnerService } from 'ngx-spinner';
import { Subscription } from 'rxjs';
import { HospitalPayload } from '../../hospital/data/hospital.payload';
import { HospitalSearchComponent } from '../../hospital/components/hospital-search/hospital-search.component';
import { ServiceGroupSearchComponent } from '../../service-group/components/service-group-search/service-group-search.component';
import { RevenueHeadPayload, ServiceGroupPayload } from '../../service-group/_data/service-group.payload';
import { RevenueHeadSearchComponent } from '../../service-group/components/revenue-head-search/revenue-head-search.component';
import { IDateRange } from '../../../shared/types/shared.interface';
import { ReportsService } from '../reports.service';
import { displayError } from '@shared';
import { RangeDatePickerComponent } from '../../../shared/components/range-date-picker/range-date-picker.component';

@Component({
    selector: 'app-report-daily-collection',
    templateUrl: './report-daily-collection.component.html',
    styles: [],
})
export class ReportDailyCollectionComponent implements OnInit, OnDestroy {
    @ViewChild('hospitalSearchComponent')
    public hospitalSearchComponent?: HospitalSearchComponent;
    @ViewChild('serviceGroupSearchComponent')
    public serviceGroupSearchComponent?: ServiceGroupSearchComponent;
    @ViewChild('revenueHeadSearchComponent')
    public revenueHeadSearchComponent?: RevenueHeadSearchComponent;
    @ViewChild('rangeDatePickerComponent')
    public rangeDatePickerComponent?: RangeDatePickerComponent;

    public groupByHospital: GroupReportEnum = GroupReportEnum.HOSPITAL;
    public groupByRevenueHead: GroupReportEnum = GroupReportEnum.REVENUE_HEAD;
    public searchPayload: ReportsSearchPayload = new ReportsSearchPayload();
    public isHospitalInvalid = false;
    public isServiceGroupInvalid = false;
    public isRevenueHeadSelectInvalid = false;
    public isLoading = false;

    private sub = new Subscription();
    constructor(
        private msg: NzMessageService,
        private toaster: ToastrService,
        private service: ReportsService,
        private spinner: NgxSpinnerService,
    ) {
    }

    ngOnInit(): void {
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    public onRadioSelectionChange(value: GroupReportEnum) {
        this.searchPayload.groupBy = value
    }

    public onHospitalSelected(hospital: HospitalPayload) {
        this.searchPayload.hospital = hospital;
        if(hospital && hospital.id) {
            this.serviceGroupSearchComponent?.getServiceGroupByHospital(hospital.id);
        }
    }

    public onServiceGroupSelected(serviceGroup: ServiceGroupPayload) {
        if (serviceGroup && serviceGroup.id) {
            this.revenueHeadSearchComponent?.getRevenueHeadsByServiceGroup(serviceGroup.id);
        }
    }

    public onRevenueHeadSelected(revenueHead: RevenueHeadPayload) {
        this.searchPayload.revenueHead = revenueHead;
    }

    public onDateRangeSelected(dateRange: IDateRange) {
        if (dateRange){
            const {startDate, endDate} = dateRange;
            this.searchPayload.dateRange = { startDate, endDate  };
        }
    }

    public onResetSearchPayload = () => {
        this.searchPayload = new ReportsSearchPayload();
        this.hospitalSearchComponent?.onClear();
        this.serviceGroupSearchComponent?.onClear();
        this.rangeDatePickerComponent?.onClear();
        this.revenueHeadSearchComponent?.onClear();
    };

    public onSearch = () => {
        const response = this.isValidSearchPayload();
        if (!response.status){
            this.toaster.error(response.message, 'Validation Error.');
            return;
        }

        this.isLoading = true;
        this.spinner.show().then();
        this.sub.add(
            this.service.getDailyCollectionReport(this.searchPayload).subscribe({
                next: (res) => {
                    this.spinner.hide().then();
                    this.isLoading = false;
                    if (res && res.byteLength > 1){
                        let file = new Blob( [res], {type: 'application/pdf'} )
                        const fileUrl = URL.createObjectURL(file);
                        window.open(fileUrl);
                    }else{
                        this.toaster.error("An error occurred, contact support for help.", '')
                    }
                },
                error: (err) => {
                    this.isLoading = false;
                    this.spinner.hide().then();
                    displayError(err, {toastService: this.toaster });
                },
            })
        )

    };

    public onCancelAction = () => {
        this.msg.info('Action Cancelled');
    };

    private isValidSearchPayload(): {status: boolean, message: string} {
        let res:  {status: boolean, message: string} = {status: true, message: ''};

        let hospital = this.searchPayload.hospital;
        if(!hospital || !hospital.id){
            res.status = false;
            res.message += 'Hospital is required. <br>';
        }

        if (hospital && hospital?.hasHospitalSoftware === false){
            if(!this.searchPayload.revenueHead || !this.searchPayload?.revenueHead?.title){
                res.status = false;
                res.message += 'The selected hospital requires revenue head. <br><br>';
            }
        }

        if (!this.searchPayload.dateRange || !this.searchPayload.dateRange.startDate || !this.searchPayload.dateRange.endDate){
            res.status = false;
            res.message += 'Date range is required. <br>';
        }
        return res;
    }

}
