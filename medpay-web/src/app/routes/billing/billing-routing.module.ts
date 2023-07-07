import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {PatientBillingComponent} from "./component/patient-billing/patient-billing.component";
import {PatientRegisterComponent} from "./component/patient-register/patient-register.component";

const routes: Routes = [
    {
        path: 'bill-patient', component: PatientBillingComponent
    },
    {
        path: 'register-patient', component: PatientRegisterComponent,
    }
];

@NgModule({
    declarations: [],
    imports: [RouterModule.forChild(routes)],
    exports: []
})
export class BillingRoutingModule {}