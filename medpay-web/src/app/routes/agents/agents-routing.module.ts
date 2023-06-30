import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AgentMakePaymentComponent } from './agent-make-payment/agent-make-payment.component';
import { AgentShiftReportComponent } from './agent-shit-report/agent-shift-report.component';
import { AgentReprintReceiptComponent } from './agent-reprint-receipt/agent-reprint-receipt.component';


const routes: Routes = [
    { path: 'make-payment', component: AgentMakePaymentComponent },
    { path: 'shift-report', component: AgentShiftReportComponent },
    { path: 'reprint-receipt', component: AgentReprintReceiptComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class AgentsRoutingModule {}