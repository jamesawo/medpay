import { Component, Input, OnInit } from '@angular/core';
import { HospitalBillItemDetailPayload } from '../../../hospital/data/hospital-bill.payload';

@Component({
    selector: 'app-agent-bill-item-table',
    templateUrl: './agent-bill-item-table.component.html',
    styles: [`
      .table {
          display: block;
          overflow-x: auto;
          white-space: nowrap;
      }
    `],
})
export class AgentBillItemTableComponent implements OnInit {
    @Input()
    public props?: {items?: HospitalBillItemDetailPayload[]} = {items: []};
    public dataList = [''];

    constructor() {
    }

    ngOnInit(): void {
    }

}
