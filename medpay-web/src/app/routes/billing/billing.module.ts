import {NgModule} from "@angular/core";
import { PatientBillingComponent } from './component/patient-billing/patient-billing.component';
import {BillingRoutingModule} from "./billing-routing.module";
import {SharedModule} from "@shared";
import {ServiceGroupModule} from "../service-group/service-group.module";
import { PatientRegisterComponent } from './component/patient-register/patient-register.component';
import { PatientSearchComponent } from './component/patient-search/patient-search.component';
import {NzSpaceModule} from "ng-zorro-antd/space";
import {NzDividerModule} from "ng-zorro-antd/divider";
import { PatientBillPaymentComponent } from './component/patient-bill-payment/patient-bill-payment.component';
import { BillItemTableComponent } from './component/_shared/bill-item-table/bill-item-table.component';
import { PatientBillSearchComponent } from './component/patient-bill-search/patient-bill-search.component';


@NgModule({
    declarations: [
    PatientBillingComponent,
    PatientRegisterComponent,
    PatientSearchComponent,
    PatientBillPaymentComponent,
    BillItemTableComponent,
    PatientBillSearchComponent
  ],
    imports: [
        BillingRoutingModule,
        SharedModule,
        ServiceGroupModule,
        NzSpaceModule,
        NzDividerModule
    ],
    exports: [
        PatientBillPaymentComponent
    ]
})
export class BillingModule {}