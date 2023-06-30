import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Subscription } from 'rxjs';
import { PassportService } from '../../../passport/service/passport.service';
import { HospitalService } from '../../../hospital/hospital.service';
import { ToastrService } from 'ngx-toastr';
import { NgxSpinnerService } from 'ngx-spinner';
import { displayError } from '@shared';
import { HospitalBillPayload } from '../../../hospital/data/hospital-bill.payload';
import { TransactionsService } from '../../../transactions/transactions.service';
import {
    TransactionPayerDetailPayload,
    TransactionPayload,
    TransactionPaymentDetailPayload,
} from '../../../transactions/_data/transaction.payload';
import { UserPayload } from '../../../users/_data/user.payload';
import { HospitalPayload } from '../../../hospital/data/hospital.payload';

@Component({
    selector: 'app-agents-bill-payment',
    templateUrl: './agents-bill-payment.component.html',
    styles: [],
})
export class AgentsBillPaymentComponent implements OnInit, OnDestroy {
    @Input()
    public props?: { hospitalId?: number };

    public billNumber: string = '';
    public isLoading = false;
    public billDetails?: HospitalBillPayload;
    public canDisplayBillDetails = false;
    public receiptDetails?: TransactionPayload;

    private sub = new Subscription();

    constructor(
        private msg: NzMessageService,
        private passportService: PassportService,
        private hospitalService: HospitalService,
        private toaster: ToastrService,
        private spinner: NgxSpinnerService,
        private transactionService: TransactionsService,
    ) {
    }

    ngOnInit(): void {
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    public onSearch = () => {
        if (!this.billNumber) {
            this.toaster.error('Enter the bill number first', 'Validation!');
            return;
        }
        this.isLoading = true;
        this.spinner.show().then();
        const payload: { bill?: string, hospitalId?: number } = {
            bill: this.billNumber,
            hospitalId: this.props?.hospitalId,
        };
        this.sub.add(
            this.hospitalService.onSearchBillDetails(payload).subscribe({
                next: (res) => {
                    this.isLoading = false;
                    this.spinner.hide().then();
                    if (res) {
                        this.canDisplayBillDetails = false;
                        this.billDetails = res;
                    }
                },
                error: (err) => {
                    this.spinner.hide().then();
                    this.isLoading = false;
                    this.canDisplayBillDetails = false;
                    displayError(err, { toastService: this.toaster });
                },
            }),
        );
    };

    public onResetSearchPayload = () => {
        this.billNumber = '';
        this.isLoading = false;
        this.billDetails = undefined;
        this.canDisplayBillDetails = false;
        this.receiptDetails = undefined;

        this.msg.success('Reset, Done!');
    };

    public onCancelAction = () => {
        this.msg.info('Action Cancelled');
    };

    public onMakePayment() {
        if (!this.billNumber) {
            this.toaster.error('Enter the bill number first', 'Validation!');
            return;
        }

        this.spinner.show().then();
        const payload = this.prepTransactionPayload(this.passportService.getLoginResponse()?.id);
        this.sub.add(
                this.transactionService.createTransaction(payload).subscribe({
                    next: (res) => {
                        this.spinner.hide().then();
                        if (res && res.id) {
                            console.log(res);
                            this.receiptDetails = res;
                        }
                    },
                    error: (err) => {
                        this.spinner.hide().then();
                        displayError(err, {toastService: this.toaster});
                    },
                })
        );
    }

    private prepTransactionPayload(userId?: number): TransactionPayload {
        let transaction = new TransactionPayload();
        transaction.user = new UserPayload(userId);
        transaction.hospital = this.getHospital();
        transaction.amount = this.getAmount();
        transaction.paymentDetail = this.getPaymentDetail();
        transaction.payerDetail = this.getPayerDetails();
        return transaction;
    }

    private getPaymentDetail(): TransactionPaymentDetailPayload {
        let payment = new TransactionPaymentDetailPayload();
        payment.billNumber = this.billDetails?.billNumber;
        return payment;
    }

    private getPayerDetails(): TransactionPayerDetailPayload {
        let payer = new TransactionPayerDetailPayload();
        payer.patientNumber = this.billDetails?.patient?.patientNumber;
        payer.fullName = this.billDetails?.patient?.fullName;
        payer.phoneNumber = this.billDetails?.patient?.phoneNumber;
        return payer;
    }

    private getAmount(): number {
        let billDetails = this.billDetails;
        if (billDetails && billDetails.total && billDetails.total.netAmount) {
            return Number.parseInt(billDetails.total.netAmount);
        }
        return 0;
    }
    private getHospital(): HospitalPayload {
        let hospital = new HospitalPayload();
        hospital.id = this.props?.hospitalId;
        return hospital;
    }

}
