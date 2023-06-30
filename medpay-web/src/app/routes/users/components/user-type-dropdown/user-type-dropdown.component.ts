import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IDropdown } from '../../../../shared/types/shared.interface';
import { splitEnum } from '@shared';
import { UserTypeEnum } from '../../_data/user.enum';

@Component({
  selector: 'app-user-type-dropdown',
  templateUrl: './user-type-dropdown.component.html',
  styles: [
  ]
})
export class UserTypeDropdownComponent implements OnInit {

    public selectOptions: IDropdown[] = [];
    public selectedValue: IDropdown = {id: 1, value: '', title: ''};

    @Input()
    public value: UserTypeEnum | undefined;

    @Input('props')
    public props: {required: boolean} = {required: false};

    @Output()
    public valueChange: EventEmitter<UserTypeEnum> = new EventEmitter<UserTypeEnum>();

    @Input()
    hasError: boolean = false;

    ngOnInit(): void {
        this.setSelectOptions();
        if (this.value && this.value.length > 1) {
            this.setDefaultSelected(this.value);
        }
    }

    public setSelectOptions(): void {
        Object.values(UserTypeEnum).forEach((value, index) => {
            this.selectOptions.push({ id: index, value: value, title: splitEnum(value) });
        });
    }

    public onSelected(selected: any): void {
        if (selected) {
            this.valueChange.emit(selected?.value);
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
    }

}
