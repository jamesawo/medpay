import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ModalHelper } from '@delon/theme';
import { HospitalPayload, HospitalSearchPayload } from '../data/hospital.payload';
import { HospitalCollectionEnum, HospitalEnvironmentEnum, HospitalSupportChannelEnum } from '../data/hospital.enum';
import { IDateRange } from '../../../shared/types/shared.interface';
import { NzMessageService } from 'ng-zorro-antd/message';
import { HospitalSearchComponent } from '../components/hospital-search/hospital-search.component';
import { HospitalSupportChannelDropdownComponent } from '../components/hospital-support-channel-dropdown/hospital-support-channel-dropdown.component';
import { RangeDatePickerComponent } from '../../../shared/components/range-date-picker/range-date-picker.component';
import { HospitalService } from '../hospital.service';
import { Subscription } from 'rxjs';
import { HospitalCollectionModeDropdownComponent } from '../components/hospital-collection-mode-dropdown/hospital-collection-mode-dropdown.component';
import { HospitalEnvironmentDropdownComponent } from '../components/hospital-environment-dropdown/hospital-environment-dropdown.component';

enum switchType {
    enabledStatus,
    hasSoftwareStatus
}

@Component({
    selector: 'app-hospital-view-hospitals',
    templateUrl: './view-hospitals.component.html',
    styles: [
        `
            nz-range-picker {
                margin: 0 8px 12px 0;
            }

            .fixed_header tbody{
              display:block;
              overflow:auto;
              width:100%;
            }
            .fixed_header thead tr{
              display:block;
            }
        `,
    ],
})
export class HospitalViewHospitalsComponent implements OnInit, OnDestroy {
    public date = null;
    public selectedValue: any = '';
    public tableData: HospitalPayload[] = [];
    public searchPayload: HospitalSearchPayload = new HospitalSearchPayload();
    public isLoadingSearchResult = false;
    public hasSoftware: switchType = switchType.hasSoftwareStatus;
    public isEnabled: switchType = switchType.enabledStatus;
    public isExpanded = false;

    @ViewChild('hospitalSearchComponent')
    private hospitalSearchComponent: HospitalSearchComponent | undefined;
    @ViewChild('channelDropdownComponent')
    private channelDropdownComponent: HospitalSupportChannelDropdownComponent | undefined;
    @ViewChild('rangeDatePickerComponent')
    private rangeDatePickerComponent: RangeDatePickerComponent | undefined;
    @ViewChild('collectionModeDropdownComponent')
    private collectionModeDropdownComponent: HospitalCollectionModeDropdownComponent | undefined;
    @ViewChild('environmentDropdownComponent')
    private environmentDropdownComponent: HospitalEnvironmentDropdownComponent | undefined;

    private subscription: Subscription = new Subscription();


    constructor(private modal: ModalHelper,
                private hospitalService: HospitalService,
                private nzMessageService: NzMessageService) {
    }


    ngOnInit(): void {

    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }


    public onHospitalSelected(value: HospitalPayload) {
        if (value) {
            const hospitals = [...this.tableData];
            hospitals.push(value);
            this.tableData = hospitals
        }
    }

    public loading = false;
    public allInCurrentPageChecked = false;
    public indeterminate = false;
    public listOfData: readonly any[] = [];
    public listOfCurrentPageData: readonly any[] = [];
    public setOfCheckedId = new Set<number>();

    public updateCheckedSet( checked: boolean, id?: number,): void {
        if (checked) {
            this.setOfCheckedId.add(id??0);
        } else {
            this.setOfCheckedId.delete(id??0);
        }
    }

    public onCurrentPageDataChange(listOfCurrentPageData: readonly any[]): void {
        this.listOfCurrentPageData = listOfCurrentPageData;
        this.refreshCheckedStatus();
    }

    public refreshCheckedStatus(): void {
        const listOfEnabledData = this.listOfCurrentPageData.filter(({ checked }) => !checked);
        this.allInCurrentPageChecked = listOfEnabledData.every(({ id }) => this.setOfCheckedId.has(id));
        this.indeterminate = listOfEnabledData.some(({ id }) => this.setOfCheckedId.has(id)) && !this.allInCurrentPageChecked;
    }

    public onItemChecked( checked: boolean, id?: number,): void {
        this.updateCheckedSet( checked, id);
        this.refreshCheckedStatus();
    }

    public onAllChecked(checked: boolean): void {
        this.listOfCurrentPageData
            .filter(({ checked }) => !checked)
            .forEach(({ id }) => this.updateCheckedSet(checked, id));
        this.refreshCheckedStatus();
    }

    public onSupportChannelSelected(value: HospitalSupportChannelEnum) {
        this.searchPayload.channelEnum = value;
    }

    public onDateRangeSelected(dateRange: IDateRange) {
        const { startDate, endDate } = dateRange;
        this.searchPayload.dateRange = { startDate, endDate };
    }

    public onResetSearchPayload = () => {
        this.hospitalSearchComponent?.onClear();
        this.channelDropdownComponent?.onClear();
        this.rangeDatePickerComponent?.onClear();
        this.collectionModeDropdownComponent?.onClear();
        this.environmentDropdownComponent?.onClear();

        this.searchPayload = new HospitalSearchPayload();
        this.tableData = [];
        this.nzMessageService.success('Reset, Done!');
    }

    public onCancelAction = () => {
        this.nzMessageService.info('Action Cancelled');
    }

    public onSearchResult = (): void => {
        this.isLoadingSearchResult = true;
        this.subscription.add(
            this.hospitalService.onSearchByPayload(this.searchPayload).subscribe({
                next: (res) => {
                    this.tableData = res;
                    this.nzMessageService.success('Success');
                },
                error: (error) => {
                    this.isLoadingSearchResult = false;
                    this.nzMessageService.error('Error');
                },
                complete: () => {
                    this.isLoadingSearchResult = false;
                },
            }),
        );
    };

    public setHospitalName(value: string) {
        this.searchPayload.hospitalName = value;
    }

    public onCollectionModeSelected(value: HospitalCollectionEnum) {
        this.searchPayload.collectionModelEnum = value;
    }

    public onEnvironmentSelected(value: HospitalEnvironmentEnum) {
        this.searchPayload.environmentEnum = value;
    }

    public onSwitchToggled(value: any, type: switchType) {
        if (type === this.hasSoftware) {
            this.searchPayload.hasHospitalSoftware = value;
        } else if (type === this.isEnabled) {
            this.searchPayload.isEnabled = value;
        }
    }
}
