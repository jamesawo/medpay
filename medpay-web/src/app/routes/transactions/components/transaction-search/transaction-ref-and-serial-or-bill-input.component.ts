import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';

export enum BillOrRefSelection {
    REFERENCE = 'REFERENCE',
    BILL_NUMBER = 'BILL_NUMBER'
}

export interface SearchValues {
    referenceValue?: string;
    serialNumberValue?: string;
    billNumberValue?: string;
    selection?: BillOrRefSelection;
}

@Component({
    selector: 'app-transaction-search',
    templateUrl: './transaction-ref-and-serial-or-bill-input.component.html',
    styles: [],
})
export class TransactionRefAndSerialOrBillInputComponent implements OnInit, OnChanges {
    public referenceOrSerial =  BillOrRefSelection.REFERENCE;
    public billNumber = BillOrRefSelection.BILL_NUMBER;

    public referenceValue?: string;
    public serialNumberValue?: string;
    public billNumberValue?: string;

    @Input()
    public props?: {enableBillNumber: boolean};

    @Input()
    public value:SearchValues = {selection: this.referenceOrSerial};

    @Output()
    public valueChange = new EventEmitter<SearchValues>();

    constructor() {
    }

    ngOnInit(): void {
    }

    ngOnChanges(changes: SimpleChanges) {
    }

    public onRadioSelectionChange(value: BillOrRefSelection): void {
        this.value.selection = value;
        if (value === BillOrRefSelection.REFERENCE){
            this.value.billNumberValue = "";
        } else if (value === BillOrRefSelection.BILL_NUMBER) {
            this.value.serialNumberValue = "";
            this.value.referenceValue = "";
        }
        this.onFieldValueChange();
    }

    public onFieldValueChange(event?: Event): void {
        this.valueChange.emit(this.value);
    }

    public onClear(): void {
        this.value = {selection: this.referenceOrSerial};
    }
}
