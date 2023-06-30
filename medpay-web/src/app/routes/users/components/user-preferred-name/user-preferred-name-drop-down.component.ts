import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IDropdown } from '../../../../shared/types/shared.interface';
import { UserPreferredNameEnum } from '../../_data/user.enum';
import { splitEnum } from '@shared';

@Component({
    selector: 'app-user-preferred-name-drop-down',
    templateUrl: './user-preferred-name-drop-down.component.html',
    styles: [],
})
export class UserPreferredNameDropDownComponent implements OnInit {
    public selectOptions: IDropdown[] = [];
    public selectedValue: IDropdown = { id: 1, value: '', title: '' };

    @Input()
    public value: UserPreferredNameEnum | undefined;

    @Input('props')
    public props: { required: boolean } = { required: false };

    @Output()
    public valueChange: EventEmitter<UserPreferredNameEnum> = new EventEmitter<UserPreferredNameEnum>();

    @Input()
    hasError: boolean = false;

    ngOnInit(): void {
        this.setSelectOptions();
        if (this.value && this.value.length > 1) {
            this.setDefaultSelected(this.value);
        }
    }

    public setSelectOptions(): void {
        Object.values(UserPreferredNameEnum).forEach((value, index) => {
            this.selectOptions.push({ id: index, value: value, title: splitEnum(value) });
        });
    }

    public onSelected(selected: any): void {
        if (selected) {
            this.valueChange.emit(selected?.value);
        } else {
            this.valueChange.emit(undefined);
        }
    }

    private setDefaultSelected(defaultValue: string): void {
        const find: any = this.selectOptions.find(option => option.value === defaultValue);
        this.selectedValue = find;
    }


}
