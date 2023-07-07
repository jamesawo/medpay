import { Component, OnDestroy, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { TransactionsService } from '../../transactions/transactions.service';
import { PassportService } from '../../passport/service/passport.service';
import { UserPayload } from '../../users/_data/user.payload';
import { Subscription } from 'rxjs';
import { ReportsService } from '../../reports/reports.service';
import { createPdfResourceUrl, displayError } from '@shared';
import { HospitalPayload } from '../../hospital/data/hospital.payload';
import { ReportsSearchPayload } from '../../reports/_data/reports.payload';

@Component({
    selector: 'app-agent-shift-report',
    templateUrl: './agent-shift-report.component.html',
    styles: [],
})
export class AgentShiftReportComponent implements OnInit, OnDestroy {

    public date?: string;
    public isLoading = false;
    public searchPayload = new ReportsSearchPayload();

    private sub = new Subscription();

    constructor(
        private spinner: NgxSpinnerService,
        private toaster: ToastrService,
        private transactionService: TransactionsService,
        private passportService: PassportService,
        private reportService: ReportsService,
    ) {
    }

    ngOnInit(): void {
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    public onDateSelected(date: string) {
        this.date = date;
    }

    public onSearch() {
        if (!this.date) {
            this.toaster.error('Select the date first', 'Validation Error');
            return;
        }

        this.prepSearchPayload();
        let response = this.isValidSearchPayload();
        if (!response.status) {
            this.toaster.error(response.reason, 'Some Error Occurred');
            return;
        }

        this.spinner.show().then();
        this.isLoading = true;

        this.sub.add(
            this.reportService.getAgentCollectionReport(this.searchPayload).subscribe({
                next: (res) => {
                    this.spinner.hide().then();
                    this.isLoading = false;
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

    }

    private prepSearchPayload(): void {
        const reportsSearchPayload = new ReportsSearchPayload();
        reportsSearchPayload.hospital = this.getHospital();
        reportsSearchPayload.user = this.getUser();
        const selectedDate = `${this.date}`;
        reportsSearchPayload.dateRange = { startDate: selectedDate, endDate: selectedDate };
        this.searchPayload = reportsSearchPayload;

    }

    private getUser(): UserPayload {
        // const loginRes = this.passportService.getLoginResponse();
        // return new UserPayload(loginRes?.id);
        return this.passportService.getUser();
    }

    private getHospital(): HospitalPayload | undefined {
        const loginRes = this.passportService.getLoginResponse();
        return loginRes?.hospital;
    }

    private isValidSearchPayload() {
        let message: { status: boolean, reason: string } = { status: true, reason: '' };
        if (!this.searchPayload.user || !this.searchPayload.user.id) {
            message.status = false;
            message.reason += 'Cannot verify your user account, re-login and try again! <br>';
        }

        if (!this.searchPayload.hospital || !this.searchPayload.hospital.id) {
            message.status = false;
            message.reason += 'Cannot find your user Hospital record! <br>';
        }

        if (!this.searchPayload.dateRange) {
            message.status = false;
            message.reason += 'Date is not supplied correctly. <br>';
        }
        return message;
    }
}
