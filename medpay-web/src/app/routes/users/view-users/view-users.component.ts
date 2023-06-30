import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { _HttpClient, ModalHelper } from '@delon/theme';
import { Subscription } from 'rxjs';
import { UserBasicDetailsPayload, UserPayload, UserSearchPayload } from '../_data/user.payload';
import { HospitalPayload } from '../../hospital/data/hospital.payload';
import { IDateRange } from '../../../shared/types/shared.interface';
import { NzMessageService } from 'ng-zorro-antd/message';
import { HospitalSearchComponent } from '../../hospital/components/hospital-search/hospital-search.component';
import { RangeDatePickerComponent } from '../../../shared/components/range-date-picker/range-date-picker.component';
import { SwitchToggleComponent } from '../../../shared/components/switch-toggle/switch-toggle.component';
import { UserTypeDropdownComponent } from '../components/user-type-dropdown/user-type-dropdown.component';
import { UserGenderDropdownComponent } from '../components/user-gender-dropdown/user-gender-dropdown.component';
import { UsersService } from '../users.service';
import { displayError, splitEnum } from '@shared';
import { ToastrService } from 'ngx-toastr';

@Component({
    selector: 'app-users-view-users',
    templateUrl: './view-users.component.html',
    styles: []
})
export class UsersViewUsersComponent implements OnInit, OnDestroy {
    public tableData: UserPayload[] = [];
    public searchPayload: UserSearchPayload = new UserSearchPayload();
    public isExpanded = false;
    public isLoading = false;

    public allInCurrentPageChecked = false;
    public loading = false;
    public indeterminate = false;
    public listOfData: readonly any[] = [];
    public listOfCurrentPageData: readonly any[] = [];
    public setOfCheckedId = new Set<number>();

    @ViewChild('hospitalSearchComponent')
    private hospitalSearchComponent: HospitalSearchComponent | undefined;
    @ViewChild('rangeDatePickerComponent')
    private rangeDatePickerComponent: RangeDatePickerComponent | undefined;
    @ViewChild('switchToggleComponent')
    private switchToggleComponent: SwitchToggleComponent | undefined;
    @ViewChild('userTypeDropdownComponent')
    private userTypeDropdownComponent: UserTypeDropdownComponent | undefined;
    @ViewChild('userGenderDropdownComponent')
    private userGenderDropdownComponent: UserGenderDropdownComponent | undefined;

    private sub = new Subscription();

    constructor(
        private http: _HttpClient,
        private modal: ModalHelper,
        private nzMessageService: NzMessageService,
        private userService: UsersService,
        private toaster: ToastrService,
    ) {
    }

    ngOnInit(): void {
    }

    onChange($event: any) {

    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    public onHospitalSelected(hospitalPayload: HospitalPayload) {
        this.searchPayload.hospital = hospitalPayload;
    }

    public onDateRangeSelected(dateRange: IDateRange) {
        this.searchPayload.dateRange = dateRange;
    }

    public onResetSearchPayload = () => {
        this.searchPayload = new UserSearchPayload();
        this.hospitalSearchComponent?.onClear();
        this.rangeDatePickerComponent?.onClear();
        this.switchToggleComponent?.onClear();
        this.userGenderDropdownComponent?.onClear();
        this.userTypeDropdownComponent?.onClear();
        this.tableData = [];
        this.nzMessageService.success('Reset, Done!');
    }

    public onCancelAction = () => {
        this.nzMessageService.info('Action Cancelled');
    }

    public onSearch = () => {
        if (!this.isHospitalSelected()){
            this.toaster.error("Please select hospital. ")
            return;
        }
        this.isLoading = true;
        this.sub.add(
            this.userService.searchWithSearchPayload(this.searchPayload).subscribe({
                next: (res) => {
                    this.isLoading = false;
                    this.tableData = res;
                    this.nzMessageService.success('Data Loaded Successfully');
                },
                error: (err) => {
                    this.isLoading = false;
                    displayError(err, { toastService: this.toaster });
                },
            }),
        );
    }

    private isHospitalSelected(): boolean {
        return !!(this.searchPayload.hospital && this.searchPayload.hospital.id);

    }

    public onCurrentPageDataChange(listOfCurrentPageData: readonly any[]): void {
        this.listOfCurrentPageData = listOfCurrentPageData;
        this.refreshCheckedStatus();
    }

    public onAllChecked(checked: boolean): void {
        this.listOfCurrentPageData
            .filter(({ checked }) => !checked)
            .forEach(({ id }) => this.updateCheckedSet(id, checked));
        this.refreshCheckedStatus();
    }

    public updateCheckedSet(id: number, checked: boolean): void {
        if (checked) {
            this.setOfCheckedId.add(id);
        } else {
            this.setOfCheckedId.delete(id);
        }
    }

    public refreshCheckedStatus(): void {
        const listOfEnabledData = this.listOfCurrentPageData.filter(({ checked }) => !checked);
        this.allInCurrentPageChecked = listOfEnabledData.every(({ id }) => this.setOfCheckedId.has(id));
        this.indeterminate = listOfEnabledData.some(({ id }) => this.setOfCheckedId.has(id)) && !this.allInCurrentPageChecked;
    }

    public onItemChecked(id: number, checked: boolean): void {
        this.updateCheckedSet(id, checked);
        this.refreshCheckedStatus();
    }

    public removeUnderscoreFromEnum(type?: string): string {
        return splitEnum(type ?? '');
    }

    public concatFullName(basicDetails?: UserBasicDetailsPayload) : string {
        if (basicDetails){
            const {firstName, lastName, otherName} = basicDetails;
            return `${firstName} ${lastName} ${otherName}`;
        }
        return '';
    }
}
