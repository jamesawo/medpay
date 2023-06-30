import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { _HttpClient, ModalHelper } from '@delon/theme';
import {
    TransactionPayerDetailPayload,
    TransactionPayload,
    TransactionPaymentDetailPayload,
    TransactionSearchPayload,
} from '../_data/transaction.payload';
import { UserPayload } from '../../users/_data/user.payload';
import { HospitalPayload } from '../../hospital/data/hospital.payload';
import { IDateRange, PageSearchPayload } from '../../../shared/types/shared.interface';
import { Subscription } from 'rxjs';
import { NzMessageService } from 'ng-zorro-antd/message';
import { ToastrService } from 'ngx-toastr';
import { HospitalSearchComponent } from '../../hospital/components/hospital-search/hospital-search.component';
import { UserSearchDropdownComponent } from '../../users/components/user-search-dropdown/user-search-dropdown.component';
import { TransactionStatusEnum } from '../_data/transaction.enum';
import { RangeDatePickerComponent } from '../../../shared/components/range-date-picker/range-date-picker.component';
import { TransactionsService } from '../transactions.service';
import {
    concatUserFullName,
    currencyFormatter,
    displayError,
    formatTime,
    getTransactionPayerDetail,
    getTransactionPaymentDetail,
    splitEnum,
} from '@shared';
import { PagePayload } from '../../../shared/types/shared.payload';
import { TransactionStatusDropdownComponent } from '../components/transaction-status-dropdown/transaction-status-dropdown.component';

@Component({
    selector: 'app-transactions-view-transactions',
    templateUrl: './view-transactions.component.html',
})
export class TransactionsViewTransactionsComponent implements OnInit, OnDestroy {
    public isExpanded: boolean = false;
    public searchPayload: TransactionSearchPayload = new TransactionSearchPayload();
    public pageRequest: PagePayload = new PagePayload();
    public isLoading = false;
    public tableData: TransactionPayload[] = [];
    public tableList = Array();
    public listOfCurrentPageData: readonly any[] = [];

    @ViewChild('hospitalSearchComponent')
    public hospitalSearchComponent?: HospitalSearchComponent;

    @ViewChild('userSearchDropdownComponent')
    public userSearchComponent?: UserSearchDropdownComponent;

    @ViewChild('rangeDatePickerComponent')
    public rangeDatePickerComponent?: RangeDatePickerComponent;

    @ViewChild('transactionStatusDropdownComponent')
    public transactionStatusDropdownComponent?: TransactionStatusDropdownComponent;

    private sub = new Subscription();

    constructor(
        private http: _HttpClient,
        private modal: ModalHelper,
        private service: TransactionsService,
        private nzMessageService: NzMessageService,
        private toaster: ToastrService,
    ) {
    }

    ngOnInit(): void {
    }

    ngOnDestroy(): void {
        this.sub.unsubscribe();
    }

    public onUserSelected(user: UserPayload) {
        if (user && user.id) {
            this.searchPayload.user = user;
        } else {
            this.searchPayload.user = undefined;
        }
    }

    public onHospitalSelected(hospital: HospitalPayload) {
        if (hospital && hospital.id) {
            this.searchPayload.hospital = hospital;
        } else {
            this.searchPayload.hospital = undefined;
        }
    }

    public onDateRangeSelected(dateRange: IDateRange) {
        if (dateRange) {
            const { startDate, endDate } = dateRange;
            this.searchPayload.dateRange = { startDate, endDate };
        } else {
            this.searchPayload.dateRange = undefined;
        }
    }

    public onSearch = () => {
        if (!this.searchPayload.hospital || !this.searchPayload.hospital.id) {
            this.toaster.error('Please select the hospital', 'Hospital is required');
            return;
        }
        const requestPayload: PageSearchPayload<TransactionSearchPayload> = {
            searchRequest: this.searchPayload,
            page: this.pageRequest,
        };

        this.isLoading = true;

        this.sub.add(
            this.service.getTransactionsBySearchPayload(requestPayload).subscribe({
                next: (res) => {
                    this.isLoading = false;
                    this.onResetTableProps();
                    if (res && res.result?.length) {
                        this.tableData = res.result;
                        this.pageRequest = res?.page!;
                        this.tableList = Array(res.page?.totalElements);
                    }
                },
                error: (err) => {
                    this.isLoading = false;
                    displayError(err, { toastService: this.toaster });
                },
            }),
        );
    };

    private onResetTableProps() : void {
        this.tableData = [];
        this.pageRequest = new PagePayload();
        this.tableList = Array();
    }

    public onResetSearchPayload = () => {
        this.searchPayload = new TransactionSearchPayload();
        this.onResetTableProps();
        this.hospitalSearchComponent?.onClear();
        this.userSearchComponent?.onClear();
        this.rangeDatePickerComponent?.onClear();
        this.transactionStatusDropdownComponent?.onClear();
        this.nzMessageService.success('Reset, Done!');
    };

    public onCancelAction = () => {
        this.nzMessageService.info('Action Cancelled');
    };

    public onTransactionStatusSelected(status: TransactionStatusEnum) {
        if (status) {
            this.searchPayload.status = status;
        } else {
            this.searchPayload.status = undefined;
        }
    }

    public onAllChecked(value: boolean) {

    }

    public onItemChecked(id?: number, value?: boolean) {

    }

    public splitEnum(value?: any): string {
        return splitEnum(value ?? '');
    }

    public concatUserName(user?: UserPayload): string {
        return concatUserFullName(user);
    }

    public getHospitalName(hospital?: HospitalPayload): string {
        const basic = hospital?.detail;
        return `${basic?.name} `;
    }

    public getPayment(paymentDetail?: TransactionPaymentDetailPayload): string {
        return getTransactionPaymentDetail(paymentDetail);
    }

    public getPayer(payerDetail?: TransactionPayerDetailPayload) {
        return getTransactionPayerDetail(payerDetail);
    }

    public getTagColor(status?: TransactionStatusEnum) {
        if (status == TransactionStatusEnum.FAILED) {
            return 'red';
        } else if (status === TransactionStatusEnum.SUCCESSFUL) {
            return 'green';
        } else {
            return 'blue';
        }
    }

    public onFormatTime(time?: string): string {
        return formatTime(time);
    }

    public onPageIndexChange(pageIndex: number) {
        this.onSearch();
    }

    public onPageSizeChange(size: number) {
        this.onSearch();
    }

    public getAmount(amount?: number) {
        return currencyFormatter(amount)
    }
}
