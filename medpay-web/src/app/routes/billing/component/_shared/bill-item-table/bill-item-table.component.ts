import {Component, Input, OnInit} from '@angular/core';
import {BillItemPayload} from "../../../_data/bill.payload";

@Component({
    selector: 'app-bill-item-table',
    templateUrl: './bill-item-table.component.html',
    styles: []
})
export class BillItemTableComponent implements OnInit {

    @Input()
    public data: BillItemPayload[] = [];

    @Input()
    public props: { allowModify: boolean } = {allowModify: false};


    constructor() {
    }

    ngOnInit(): void {
    }

    public onRemoveBillItem(itemIndex: number) {
        const items = [...this.data];
        let filter = items.filter((value, index) => index != itemIndex);
        this.data = [...filter];

    }

}
