import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IDropdown } from '../../../../shared/types/shared.interface';
import { ServiceCategoryEnum } from '../../_data/service-group.enum';
import { splitEnum } from '@shared';

@Component({
  selector: 'app-revenue-head-category-dropdown',
  templateUrl: './revenue-head-category-dropdown.component.html',
  styles: [
  ]
})
export class RevenueHeadCategoryDropdownComponent implements OnInit {
    public selectOptions: IDropdown[] = [];
    public selectedValue: IDropdown = {id: 1, value: '', title: ''};
    @Input()
    public props: {required: boolean} = {required: false};

    @Input()
    public value?: ServiceCategoryEnum;

    @Output()
    public valueChange: EventEmitter<ServiceCategoryEnum> = new EventEmitter<ServiceCategoryEnum>();

    @Input()
    hasError: boolean = false;

    ngOnInit(): void {
        this.setSelectOptions();
        if (this.value && this.value.length > 1) {
            this.setDefaultSelected(this.value);
        }
    }

    public setSelectOptions(): void {
        Object.values(ServiceCategoryEnum).forEach((value, index) => {
            this.selectOptions.push({ id: index, value: value, title: splitEnum(value) });
        });
    }

    public onSelected(selected: any): void {
        if (selected) {
            this.valueChange.emit(selected.value);
        }else{
            this.valueChange.emit(undefined);
        }
    }

    private setDefaultSelected(defaultValue: string): void {
        const find: any = this.selectOptions.find(option => option.value === defaultValue);
        this.selectedValue = find;
    }

    public onClear() {
        this.value = undefined;
        this.selectedValue = {id: 1, value: '', title: ''};
    }

}
