import {Component, OnInit} from '@angular/core';
import {BillPayload, BillStatus} from "../../_data/bill.payload";
import {PatientPayload} from "../../_data/patient.payload";
import {HospitalPayload} from "../../../hospital/data/hospital.payload";
import {
    TransactionPayerDetailPayload,
    TransactionPayload,
    TransactionPaymentDetailPayload
} from "../../../transactions/_data/transaction.payload";
import {HospitalCollectionEnum} from "../../../hospital/data/hospital.enum";
import {UserPayload} from "../../../users/_data/user.payload";
import {PassportService} from "../../../passport/service/passport.service";
import {TransactionsService} from "../../../transactions/transactions.service";
import {NgxSpinnerService} from "ngx-spinner";
import {NzMessageService} from "ng-zorro-antd/message";
import {RemittaGatewayService} from "../../../gateway/remitta-gateway.service";

@Component({
    selector: 'app-patient-bill-payment',
    templateUrl: './patient-bill-payment.component.html',
    styles: []
})
export class PatientBillPaymentComponent implements OnInit {
    public bill: BillPayload = new BillPayload();
    public transaction?: TransactionPayload;
    public showSuccessTransaction = false;
    public paid = BillStatus.PAID;

    constructor(
        private passportService: PassportService,
        private transactionService: TransactionsService,
        private spinner: NgxSpinnerService,
        private msg: NzMessageService,
        private gatewayService: RemittaGatewayService
    ) {
    }

    ngOnInit(): void {
    }

    public onBillSelected(bill: BillPayload) {
        if (bill) {
            this.bill = bill;
        }
    }

    public contactFullName(patient?: PatientPayload) {
        return `${patient?.firstName} ${patient?.lastName} ${patient?.otherName}`;
    }

    public onProcessPayment() {

        const hospital = this.bill.hospital;
        const collectionMode = this.getCollectionMode(hospital);
        this.processPaymentByCollectionMode(this.bill, collectionMode);

    }

    public onResetSearchPayload = () => {
        this.bill = new BillPayload();
        this.msg.success('Reset, Done!');
    };

    public onCancelAction = () => {
        this.msg.info('Action Cancelled');
    };

    private getCollectionMode(hospital?: HospitalPayload) {
        return hospital?.collectionModelEnum ?? HospitalCollectionEnum.MANUAL;
    }

    private processPaymentByCollectionMode(bill: BillPayload, mode: HospitalCollectionEnum) {
        const transaction = this.getTransactionPayload(bill);
        this.makePayment(transaction, mode);
    }

    private getTransactionPayload(bill: BillPayload) {
        let transaction = new TransactionPayload();
        transaction.user = new UserPayload(this.passportService.getLoginResponse()?.id);
        transaction.hospital = new HospitalPayload(bill.hospital?.id);
        transaction.amount = bill.billAmount;
        transaction.paymentDetail = this.getPaymentDetails(bill);
        transaction.payerDetail = this.getPayerDetails(bill);
        return transaction;
    }

    private getPaymentDetails(bill: BillPayload) {
        let details = new TransactionPaymentDetailPayload();
        details.billNumber = bill.billNumber;
        details.services = bill.items.map(value => value.service!);
        return details;
    }

    private getPayerDetails(bill: BillPayload): TransactionPayerDetailPayload {
        let payer = new TransactionPayerDetailPayload();
        payer.patientNumber = bill?.patient?.uniqueCode;
        payer.fullName = `${bill?.patient?.firstName} ${bill?.patient?.lastName} ${bill?.patient?.otherName}`
        payer.phoneNumber = bill?.patient?.phone;
        return payer;
    }


    private onGatewayHandleTransaction(transaction: TransactionPayload) {
        console.log('call payment gateway');
        this.gatewayService.generateRRR(transaction).subscribe({
            next: (res) => {
                console.log(res);
            },

            error: (err) => {
                console.log(err)
            }
        })

    }

    private makePayment(transaction: TransactionPayload, mode: HospitalCollectionEnum) {
        this.spinner.show().then();
        if (mode == HospitalCollectionEnum.MANUAL) {
            this.createTransaction(transaction);
        } else {
            this.onGatewayHandleTransaction(transaction);
        }

    }

    private createTransaction(transaction: TransactionPayload) {
        this.transactionService.createTransaction(transaction).subscribe({
            next: (response) => {
                if (response && response.id) {
                    this.spinner.hide().then();
                    this.transaction = response;
                    this.showSuccessTransaction = true;
                }
            },
            error: (err) => {
                console.log(err);
                this.spinner.hide().then();
                this.msg.error('An error occurred');
            },
        })
    }

}
