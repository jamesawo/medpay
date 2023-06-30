import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IDropdown } from '../../../../shared/types/shared.interface';
import { HospitalSupportChannelEnum } from '../../data/hospital.enum';
import { splitEnum } from '../../../../shared/utils/utility';


@Component({
    selector: 'app-hospital-support-channel-dropdown',
    templateUrl: './hospital-support-channel-dropdown.component.html',
    styles: [],
})
export class HospitalSupportChannelDropdownComponent implements OnInit {

    public selectOptions: IDropdown[] = [];
    public selectedValue: IDropdown = {id: 1, value: '', title: ''};

    @Input('default')
    public defaultSelected: HospitalSupportChannelEnum | undefined;

    @Output()
    public selected: EventEmitter<HospitalSupportChannelEnum> = new EventEmitter<HospitalSupportChannelEnum>();

    ngOnInit(): void {
        this.setSelectOptions();
        if (this.defaultSelected && this.defaultSelected.length > 1) {
            this.setDefaultSelected(this.defaultSelected);
        }
    }

    public setSelectOptions(): void {
        Object.values(HospitalSupportChannelEnum).forEach((value, index) => {
            this.selectOptions.push({ id: index, value: value, title: splitEnum(value) });
        });
    }

    public onSelected(selected: any): void {
        if (selected) {
            this.selected.emit(selected.value);
        }else{
            this.selected.emit(undefined);
        }
    }

    public onClear() {
        this.selectedValue = {id: 1, value: '', title: ''};
    }

    private setDefaultSelected(defaultValue: string): void {
        const find: any = this.selectOptions.find(option => option.value === defaultValue);
        this.selectedValue = find;
    }


}
