import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IDropdown } from '../../../../shared/types/shared.interface';
import { HospitalAuthTypeEnum } from '../../data/hospital.enum';
import { splitEnum } from '../../../../shared/utils/utility';

@Component({
  selector: 'app-hospital-auth-type-dropdown',
  templateUrl: './hospital-auth-type-dropdown.component.html',
  styles: [
  ]
})
export class HospitalAuthTypeDropdownComponent implements OnInit {

    public selectOptions: IDropdown[] = [];
    public selectedValue: IDropdown = {id: 1, value: '', title: ''};

    @Input('default')
    public defaultSelected: HospitalAuthTypeEnum | undefined;

    @Input('props')
    public props: {required: boolean} = {required: false};

    @Output()
    public selected: EventEmitter<HospitalAuthTypeEnum> = new EventEmitter<HospitalAuthTypeEnum>();

    @Input()
    hasError: boolean = false;

    ngOnInit(): void {
        this.setSelectOptions();
        if (this.defaultSelected && this.defaultSelected.length > 1) {
            this.setDefaultSelected(this.defaultSelected);
        }
    }

    public setSelectOptions(): void {
        Object.values(HospitalAuthTypeEnum).forEach((value, index) => {
            this.selectOptions.push({ id: index, value: value, title: splitEnum(value) });
        });
    }

    public onSelected(selected: any): void {
        if (selected) {
            this.selected.emit(selected.value);
        }
    }

    private setDefaultSelected(defaultValue: string): void {
        const find: any = this.selectOptions.find(option => option.value === defaultValue);
        this.selectedValue = find;
    }


}
