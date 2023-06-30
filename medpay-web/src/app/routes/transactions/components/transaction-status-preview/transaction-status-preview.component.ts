import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { TransactionPayload } from '../../_data/transaction.payload';
import { NzResultStatusType } from 'ng-zorro-antd/result';
import { TransactionStatusEnum } from '../../_data/transaction.enum';
import { concatUserFullName, createPdfResourceUrl, displayError, formatTime, getTransactionPayerDetail } from '@shared';
import { formatDate } from '@angular/common';
import { NgxSpinnerService } from 'ngx-spinner';
import { Subscription } from 'rxjs';
import { TransactionsService } from '../../transactions.service';
import { ToastrService } from 'ngx-toastr';

@Component({
    selector: 'app-transaction-status-preview',
    templateUrl: './transaction-status-preview.component.html',
    styles: [],
})
export class TransactionStatusPreviewComponent implements OnInit, OnDestroy {

    public detailTitle?: string;
    public detailSubTitle?: string;
    public successStatus = TransactionStatusEnum.SUCCESSFUL;
    public receiptDataUrl?: ArrayBuffer;

    @Input()
    props: {
        data?: TransactionPayload,
        canDownloadReceipt?: boolean,
        canShowDetails?: boolean,
        onResetAction: () => void,
        onCancelAction: () => void,
    } = {
        onResetAction: () => {},
        onCancelAction: () => {},
    };

    private sub = new Subscription();
    constructor(
        private spinner: NgxSpinnerService,
        private service: TransactionsService,
        private toaster: ToastrService,
        ) {
    }

    ngOnInit(): void {
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    public getTransactionStatus(): NzResultStatusType {
        if (this.props?.data) {
            switch (this.props?.data.statusEnum) {
                case TransactionStatusEnum.SUCCESSFUL:
                    this.detailTitle = 'STATUS:  SUCCESSFUL';
                    return 'success';
                case TransactionStatusEnum.FAILED:
                    this.detailTitle = 'STATUS:  FAILED';
                    return 'error';
                case TransactionStatusEnum.INITIATED:
                    this.detailTitle = 'STATUS:  IN PROGRESS';
                    return 'info';
                case TransactionStatusEnum.CANCELLED:
                    this.detailTitle = 'STATUS:  CANCELLED';
                    return 'error';
            }
        }
        return '404';
    }

    public onFormatTime(time?: string): string {
        return formatTime(this.props?.data?.time);
    }

    public getUserName(): string {
        return concatUserFullName(this.props?.data?.user);
    }

    public getPayerInfo(): string {
        return getTransactionPayerDetail(this.props?.data?.payerDetail);
    }

    public getSubDetail(): string {
        return this.detailSubTitle = `
            REF#: ${this.props?.data?.reference} SERIAL#: ${this.props?.data?.token} DATE: ${this.onFormatDate()}
        `;
    }

    public getHospitalName(): string {
        return `${this.props?.data?.hospital?.detail.name}`;
    }

    public onFormatDate(): string{
        return formatDate(this.props?.data?.date ?? '', 'MMM dd yyyy', 'en_US');
    }

    public isSuccessfulTransaction(): boolean {
        if(this.props && this.props.data){
            return this.props.data.statusEnum === TransactionStatusEnum.SUCCESSFUL;
        }
        return false;
    }

    public getDate(): string {
        return this.props?.data?.date!;
    }

    public getAmount(): number {
        return this.props?.data?.amount!;
    }

    public getPhone(): string {
        return this.props?.data?.payerDetail?.phoneNumber!;
    }

    public onPreviewReceipt(){
        this.spinner.show().then();
        if(this.props && this.props.data) {
            const { reference, token } = this.props!.data!;
            if (this.isSuccessfulTransaction()) {
                this.sub.add(
                    this.service.getTransactionReceiptPdfBytes({ reference, serial: token }).subscribe({
                        next: (res: any) => {
                            this.spinner.hide().then();
                            this.receiptDataUrl = res;
                        },
                        error: (err: any) => {
                            this.spinner.hide().then();
                            displayError(err, { toastService: this.toaster });
                        }
                    })
                )
            }
        }
    }

    public onDownloadReceipt = () => {
        if( this.props && this.props.data && this.receiptDataUrl){
            const fileURL = createPdfResourceUrl(this.receiptDataUrl);
            let anchorElement = document.createElement('a');
            document.body.appendChild(anchorElement);
            anchorElement.setAttribute('style', 'display: none');
            anchorElement.href = fileURL;
            anchorElement.download = `PaymentReceipt-${this.props?.data?.reference}.pdf`;
            anchorElement.click();
            window.URL.revokeObjectURL(fileURL);
            anchorElement.remove();
        }
    };

    public onPrintAction = () => {
        if( this.receiptDataUrl){
            const fileURL = createPdfResourceUrl(this.receiptDataUrl);
            let openWindow:any  = window.open(`${fileURL}`, '', 'height=480px, width=640px');
            openWindow.focus();
            openWindow.print();
        }
    };

}
