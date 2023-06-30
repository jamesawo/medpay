import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IDropdown } from '../../../../shared/types/shared.interface';
import { HospitalEnvironmentEnum } from '../../data/hospital.enum';
import { splitEnum } from '../../../../shared/utils/utility';


@Component({
  selector: 'app-hospital-environment-dropdown',
  templateUrl: './hospital-environment-dropdown.component.html',
  styles: [
  ]
})
export class HospitalEnvironmentDropdownComponent implements OnInit {

    public selectOptions: IDropdown[] = [];
    public selectedValue: IDropdown = {id: 1, value: '', title: ''};

    @Input('default')
    public defaultSelected: HospitalEnvironmentEnum | undefined;

    @Input('props')
    public props: {required: boolean} = {required: false};

    @Input('hasError')
    public hasError: boolean = false;

    @Output()
    public selected: EventEmitter<HospitalEnvironmentEnum> = new EventEmitter<HospitalEnvironmentEnum>();


    ngOnInit(): void {
        this.setSelectOptions();
        if (this.defaultSelected && this.defaultSelected.length > 1) {
            this.setDefaultSelected(this.defaultSelected);
        }
    }

    public setSelectOptions(): void {
        Object.values(HospitalEnvironmentEnum).forEach((value, index) => {
            this.selectOptions.push({ id: index, value: value, title: splitEnum(value) });
        });
    }

    public onSelected(selected: any): void {
        if (selected) {
            this.selected.emit(selected.value);
        }else {
            this.selected.emit(undefined);
        }
    }

    private setDefaultSelected(defaultValue: string): void {
        const find: any = this.selectOptions.find(option => option.value === defaultValue);
        this.selectedValue = find;
    }

    public onClear(){
        this.selectedValue = {id: 1, value: '', title: ''};
    }

}


