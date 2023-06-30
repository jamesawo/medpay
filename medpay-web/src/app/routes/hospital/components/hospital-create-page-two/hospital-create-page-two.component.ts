import { Component, Input, OnInit } from '@angular/core';
import { HospitalPayload } from '../../data/hospital.payload';
import { HospitalCollectionEnum, HospitalSupportChannelEnum } from '../../data/hospital.enum';


@Component({
    selector: 'app-hospital-create-page-two',
    templateUrl: './hospital-create-page-two.component.html',
    styles: [],
})
export class HospitalCreatePageTwoComponent implements OnInit {

    @Input('payload')
    public payload: HospitalPayload = new HospitalPayload();

    @Input('addStep') addStep: ((args: any) => void) | undefined;

    constructor() {
    }

    ngOnInit(): void {
    }


    public onSupportChannelSelected(value: HospitalSupportChannelEnum) {
        if (value) {
            this.payload.detail.supportChannel = value;
        }
    }

    public onCollectionModeSelected(value: HospitalCollectionEnum) {
        if (value) {
            this.payload.collectionModelEnum = value;
        }
    }

    public onExpirationDateSelected(date: string) {
        if (date) {
            this.payload.expiryDate = date;
        }
    }

    public onHasSoftwareSelected(value: any) {
        if (this.addStep) {
            this.addStep(value);
        }
    }
}
