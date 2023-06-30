import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IDropdown } from '../../../../shared/types/shared.interface';
import { splitEnum } from '@shared';
import { TransactionStatusEnum } from '../../_data/transaction.enum';

@Component({
  selector: 'app-transaction-status-dropdown',
  templateUrl: './transaction-status-dropdown.component.html',
  styles: [
  ]
})
export class TransactionStatusDropdownComponent implements OnInit{

    public selectOptions: IDropdown[] = [];
    public selectedValue: IDropdown = {id: 1, value: '', title: ''};

    @Input()
    public value: TransactionStatusEnum | undefined;

    @Input('props')
    public props: {required: boolean} = {required: false};

    @Output()
    public valueChange: EventEmitter<TransactionStatusEnum> = new EventEmitter<TransactionStatusEnum>();

    @Input()
    hasError: boolean = false;

    ngOnInit(): void {
        this.setSelectOptions();
        if (this.value && this.value.length > 1) {
            this.setDefaultSelected(this.value);
        }
    }

    public setSelectOptions(): void {
        Object.values(TransactionStatusEnum).forEach((value, index) => {
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
        this.selectedValue = { id: 0, value: '', title: '' };
    }
    
}
