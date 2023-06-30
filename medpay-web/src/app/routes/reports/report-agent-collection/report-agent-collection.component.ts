import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { IDateRange } from '../../../shared/types/shared.interface';
import { ReportsSearchPayload } from '../_data/reports.payload';
import { Subscription } from 'rxjs';
import { HospitalSearchComponent } from '../../hospital/components/hospital-search/hospital-search.component';
import { NzMessageService } from 'ng-zorro-antd/message';
import { ToastrService } from 'ngx-toastr';
import { ReportsService } from '../reports.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { UserSearchDropdownComponent } from '../../users/components/user-search-dropdown/user-search-dropdown.component';
import { UserPayload } from '../../users/_data/user.payload';
import { createPdfResourceUrl, displayError } from '@shared';
import { HospitalPayload } from '../../hospital/data/hospital.payload';


@Component({
    selector: 'app-report-agent-collection',
    templateUrl: './report-agent-collection.component.html',
    styles: [],
})
export class ReportAgentCollectionComponent implements OnInit, OnDestroy {

    public searchPayload = new ReportsSearchPayload();
    @ViewChild('hospitalSearchComponent')
    public hospitalSearchComponent?: HospitalSearchComponent;
    @ViewChild('userSearchDropdownComponent')
    public userSearchDropdownComponent?: UserSearchDropdownComponent;

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

    public onDateRangeSelected(dateRange: IDateRange) {
        const { startDate, endDate } = dateRange;
        this.searchPayload.dateRange = { startDate, endDate };
    }

    public onUserSelected(user: UserPayload) {
        const canSelect = this.canSelectUser(user);
        if (!canSelect.status) {
            this.toaster.error(canSelect.message, 'Incorrect selected');
            return
        }
        this.searchPayload.user = user;
    }

    private canSelectUser(user: UserPayload) {
        let res: {status: boolean, message: string} = {status: true, message: ''};
        if (!this.searchPayload.hospital){
            return {status: false, message: 'Hospital is required first'};
        }

        let isMatch = this.isUserMatchHospital(user);
        if(!isMatch) {
            this.userSearchDropdownComponent?.onClear();
            return {status: false, message: 'Selected user is not a member of the selected hospital'};
        }

        return res;

    }

    private isUserMatchHospital(user: UserPayload): boolean {
        let hospital = this.searchPayload?.hospital;
        if (hospital){
            return hospital && hospital.id === user.hospital.id
        }
        return false;
    }

    public onHospitalSelected(hospital: HospitalPayload) {
        this.searchPayload.hospital = hospital;
        this.searchPayload.user = undefined;
        this.userSearchDropdownComponent?.onClear();
    }

    public onResetSearchPayload = () => {
        this.searchPayload = new ReportsSearchPayload();
        this.hospitalSearchComponent?.onClear();
        this.userSearchDropdownComponent?.onClear();
        this.msg.info('Reset Done!');
    };

    public onSearch = () => {
        const res = this.isValidSearchPayload();
        if (!res.status) {
            this.toaster.error(res.message, 'Validation Error');
            return;
        }
        this.isLoading = true;
        this.spinner.show().then();
        this.sub.add(
            this.service.getAgentCollectionReport(this.searchPayload).subscribe({
                next: (res) => {
                    this.isLoading = false;
                    this.spinner.hide().then();
                    if (res) {
                        const fileUrl = createPdfResourceUrl(res);
                        window.open(fileUrl);
                    }
                },
                error: (err) => {
                    this.isLoading = false;
                    this.spinner.hide().then();
                    displayError(err, { toastService: this.toaster });
                },
            }),
        );
    };

    public onCancelAction = () => {
        this.msg.info('Action Cancelled');
    };

    private isValidSearchPayload(): { status: boolean, message: string } {
        let res: { status: boolean, message: string } = { status: true, message: '' };

        if (!this.searchPayload.hospital || !this.searchPayload.hospital.id) {
            res.status = false;
            res.message += 'Hospital is required. <br>';
        }


        if (!this.searchPayload.user || !this.searchPayload.user.id) {
            res.status = false;
            res.message += 'User is required. <br>';
        }

        if (!this.searchPayload.dateRange || !this.searchPayload.dateRange.startDate || !this.searchPayload.dateRange.endDate) {
            res.status = false;
            res.message += 'Date range is required. <br>';
        }

        return res;
    }
}
