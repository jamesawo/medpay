import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IDropdown } from '../../../../shared/types/shared.interface';
import { UserLimitTypeEnum } from '../../_data/user.enum';
import { splitEnum } from '@shared';

@Component({
  selector: 'app-user-limit-type-dropdown',
  templateUrl: './user-limit-type-dropdown.component.html',
  styles: [
  ]
})
export class UserLimitTypeDropdownComponent implements OnInit {

    public selectOptions: IDropdown[] = [];
    public selectedValue: IDropdown = {id: 1, value: '', title: ''};

    @Input()
    public value?: UserLimitTypeEnum;

    @Input('props')
    public props: {required: boolean} = {required: false};

    @Output()
    public valueChange: EventEmitter<UserLimitTypeEnum> = new EventEmitter<UserLimitTypeEnum>();

    @Input()
    hasError: boolean = false;

    ngOnInit(): void {
        this.setSelectOptions();
        if (this.value && this.value.length > 1) {
            this.setDefaultSelected(this.value);
        }
    }

    public setSelectOptions(): void {
        Object.values(UserLimitTypeEnum).forEach((value, index) => {
            this.selectOptions.push({ id: index, value: value, title: splitEnum(value) });
        });
    }

    public onSelected(selected: any): void {
        if (selected) {
            this.valueChange.emit(selected.value);
        }
    }

    private setDefaultSelected(defaultValue: string): void {
        const find: any = this.selectOptions.find(option => option.value === defaultValue);
        this.selectedValue = find;
    }
}
