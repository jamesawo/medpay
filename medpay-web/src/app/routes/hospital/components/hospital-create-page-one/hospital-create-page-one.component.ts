import { Component, Input, OnInit } from '@angular/core';
import { HospitalPayload } from '../../data/hospital.payload';

@Component({
    selector: 'app-hospital-create-page-one',
    templateUrl: './hospital-create-page-one.component.html',
    styles: [],
})
export class HospitalCreatePageOneComponent implements OnInit {

    @Input('payload')
    public payload: HospitalPayload = new HospitalPayload();

    constructor() {
    }

    ngOnInit(): void {
    }

}
