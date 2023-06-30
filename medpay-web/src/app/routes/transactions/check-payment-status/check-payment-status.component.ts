import { Component, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Subscription } from 'rxjs';
import { NzMessageService } from 'ng-zorro-antd/message';
import { ToastrService } from 'ngx-toastr';
import { TransactionPayload, TransactionSearchPayload } from '../_data/transaction.payload';
import {
    BillOrRefSelection,
    SearchValues,
    TransactionRefAndSerialOrBillInputComponent,
} from '../components/transaction-search/transaction-ref-and-serial-or-bill-input.component';
import { TransactionsService } from '../transactions.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { currencyFormatter, displayError } from '@shared';
import { TransactionStatusEnum } from '../_data/transaction.enum';
import { TransactionReceiptViewerComponent } from '../../../shared/components/transaction-receipt-viewer/transaction-receipt-viewer.component';

@Component({
    selector: 'app-transactions-check-payment-status',
    templateUrl: './check-payment-status.component.html',
})
export class TransactionsCheckPaymentStatusComponent implements OnInit, OnDestroy {
    @ViewChild('transactionSearchComponent')
    public transactionSearchComponent?: TransactionRefAndSerialOrBillInputComponent;

    @ViewChild('transactionReceiptViewerComponent')
    public transactionReceiptViewerComponent?: TransactionReceiptViewerComponent;

    public searchPayload: TransactionSearchPayload = new TransactionSearchPayload();
    public isLoading = false;
    public searchValues: SearchValues = { selection: BillOrRefSelection.REFERENCE };
    public result?: TransactionPayload;

    public successStatus: TransactionStatusEnum = TransactionStatusEnum.SUCCESSFUL;
    @Input()
    public props?: {canShowDetails: boolean, pageTitle: string, canDownloadReceipt: boolean };


    private sub = new Subscription();
    constructor(
        private nzMessageService: NzMessageService,
        private toaster: ToastrService,
        private service: TransactionsService,
        private spinner: NgxSpinnerService,
    ) {
    }

    ngOnInit(): void {
        if(!this.props){
            this.props = {canShowDetails: false, pageTitle: 'Transaction Status', canDownloadReceipt: false};
        }
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    public get amount(){ return currencyFormatter(this.result?.amount)};

    public onSearch = () => {
        // this.searchValues = {...this.searchValues, serialNumberValue: '913955228069', referenceValue: '3542290400'};
        const result = this.isValidInputSearch(this.searchValues);
        if (!result.status) {
            this.toaster.error(result.message, 'Invalid Search.');
            return;
        }

        this.isLoading = true;
        this.spinner.show().then();
        const { serialNumberValue, referenceValue } = this.searchValues;
        this.sub.add(
            this.service.getTransactionBySerialAndRef({
                serial: serialNumberValue,
                reference: referenceValue,
            }).subscribe({
                next: (res) => {
                    this.spinner.hide().then();
                    this.isLoading = false;
                    if (res) {
                        this.result = res;
                    } else {
                        this.nzMessageService.info('No data found, try again with different receipt info');
                    }
                },
                error: (err) => {
                    this.spinner.hide().then();
                    this.isLoading = false;
                    displayError(err, { toastService: this.toaster });
                },
            }),
        );
    };

    public onResetSearchPayload = () => {
        this.searchPayload = new TransactionSearchPayload();
        this.result = undefined;
        this.transactionSearchComponent?.onClear();
        this.transactionReceiptViewerComponent?.onClear();
        this.nzMessageService.info('Rest Done!.');
    };

    public onCancelAction = () => {
        this.nzMessageService.info('Action Cancelled');
    };

    protected isValidInputSearch(searchValues: SearchValues): { status: boolean, message: string } {
        let res: { status: boolean, message: string } = { status: true, message: '' };
        const { selection, serialNumberValue, billNumberValue, referenceValue } = searchValues;
        if (selection && selection == BillOrRefSelection.REFERENCE) {
            if (!serialNumberValue) {
                res.status = false;
                res.message += 'Enter a valid serial number <br>';
            }
            if (!referenceValue) {
                res.status = false;
                res.message += 'Enter a valid reference number <br>';
            }
        }
        if (selection && selection == BillOrRefSelection.BILL_NUMBER) {
            if (!billNumberValue) {
                res.status = false;
                res.message += 'Enter a valid bill number <br>';
            }
        }
        return res;
    }

}
