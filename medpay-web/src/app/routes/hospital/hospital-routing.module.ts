import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HospitalViewHospitalsComponent } from './view-hospitals/view-hospitals.component';
import { HospitalCreateHospitalComponent } from './create-hospital/create-hospital.component';
import { HospitalConfigureHospitalComponent } from './configure-hospital/configure-hospital.component';

const routes: Routes = [
    { path: 'view-hospitals', component: HospitalViewHospitalsComponent },
    { path: 'create-hospital', component: HospitalCreateHospitalComponent },
    { path: 'configure-hospital', component: HospitalConfigureHospitalComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class HospitalRoutingModule {}
