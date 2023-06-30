import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IDropdown } from '../../../../shared/types/shared.interface';
import { HospitalCollectionEnum } from '../../data/hospital.enum';
import { splitEnum } from '../../../../shared/utils/utility';

@Component({
  selector: 'app-hospital-collection-mode-dropdown',
  templateUrl: './hospital-collection-mode-dropdown.component.html',
  styles: [
  ]
})
export class HospitalCollectionModeDropdownComponent implements OnInit {

    public selectOptions: IDropdown[] = [];
    public selectedValue: IDropdown = {id: 1, value: '', title: ''};

    @Input('default')
    public defaultSelected: HospitalCollectionEnum | undefined;

    @Input('props')
    public props: {required: boolean} = {required: false};

    @Input()
    public hasError = false;

    @Output()
    public selected: EventEmitter<HospitalCollectionEnum> = new EventEmitter<HospitalCollectionEnum>();


    ngOnInit(): void {
        this.setSelectOptions();
        if (this.defaultSelected && this.defaultSelected.length > 1) {
            this.setDefaultSelected(this.defaultSelected);
        }
    }

    public setSelectOptions(): void {
        Object.values(HospitalCollectionEnum).forEach((value, index) => {
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

    private setDefaultSelected(defaultValue: string): void {
        const find: any = this.selectOptions.find(option => option.value === defaultValue);
        this.selectedValue = find;
    }

    public onClear(){
        this.selectedValue = {id: 1, value: '', title: ''};
    }

}
