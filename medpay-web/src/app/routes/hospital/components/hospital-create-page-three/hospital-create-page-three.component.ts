import { Component, Input, OnInit } from '@angular/core';
import { HospitalPayload } from '../../data/hospital.payload';
import { HospitalAuthTypeEnum, HospitalEnvironmentEnum } from '../../data/hospital.enum';

@Component({
    selector: 'app-hospital-create-page-three',
    templateUrl: './hospital-create-page-three.component.html',
    styles: [],
})
export class HospitalCreatePageThreeComponent implements OnInit {

    @Input('payload')
    public payload: HospitalPayload = new HospitalPayload();

    public isLive: boolean =  false;

    constructor() {
    }

    ngOnInit(): void {
        this.onSwitchToggled(this.payload.apiConfiguration.environmentEnum === HospitalEnvironmentEnum.LIVE);
    }

    public onAuthTypeSelected(authTypeEnum: HospitalAuthTypeEnum) {
        if (authTypeEnum){
            this.payload.apiConfiguration.authenticationType = authTypeEnum;
        }
    }

    public onSwitchToggled(value: boolean) {
        this.isLive = value;
        this.payload.apiConfiguration.environmentEnum = value ? HospitalEnvironmentEnum.LIVE : HospitalEnvironmentEnum.TEST;
    }


}
