import { NgModule, Type } from '@angular/core';
import { SharedModule } from '@shared';
import { HospitalCreateHospitalComponent } from './create-hospital/create-hospital.component';
import { HospitalRoutingModule } from './hospital-routing.module';
import { HospitalService } from './hospital.service';
import { HospitalViewHospitalsComponent } from './view-hospitals/view-hospitals.component';
import { HospitalCreatePageOneComponent } from './components/hospital-create-page-one/hospital-create-page-one.component';
import { HospitalCreatePageTwoComponent } from './components/hospital-create-page-two/hospital-create-page-two.component';
import { HospitalCreatePageThreeComponent } from './components/hospital-create-page-three/hospital-create-page-three.component';

const COMPONENTS: Array<Type<void>> = [
    HospitalViewHospitalsComponent,
    HospitalCreateHospitalComponent,
    HospitalCreatePageOneComponent,
    HospitalCreatePageTwoComponent,
    HospitalCreatePageThreeComponent,
];

@NgModule({
    imports: [SharedModule, HospitalRoutingModule],
    declarations: COMPONENTS,
    providers: [HospitalService],
    exports: [
    ],
})
export class HospitalModule {}
