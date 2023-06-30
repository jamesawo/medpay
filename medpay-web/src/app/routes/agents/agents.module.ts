import { NgModule, Type } from '@angular/core';
import { SharedModule } from '@shared';
import { AgentsRoutingModule } from './agents-routing.module';
import { AgentsService } from './agents.service';
import { AgentMakePaymentComponent } from './agent-make-payment/agent-make-payment.component';
import { AgentShiftReportComponent } from './agent-shit-report/agent-shift-report.component';
import { ServiceGroupModule } from '../service-group/service-group.module';
import { AgentsServicePaymentComponent } from './components/agents-service-payment/agents-service-payment.component';
import { AgentsBillPaymentComponent } from './components/agents-bill-payment/agents-bill-payment.component';
import { AgentReprintReceiptComponent } from './agent-reprint-receipt/agent-reprint-receipt.component';
import { TransactionsModule } from '../transactions/transactions.module';
import { AgentBillItemTableComponent } from './components/agent-bill-item-table/agent-bill-item-table.component';


const COMPONENTS: Array<Type<void>> = [
    AgentMakePaymentComponent,
    AgentShiftReportComponent,
    AgentsServicePaymentComponent,
    AgentsBillPaymentComponent,
    AgentReprintReceiptComponent,
    AgentBillItemTableComponent
];

@NgModule({
    imports: [SharedModule, AgentsRoutingModule, ServiceGroupModule, TransactionsModule],
    declarations: [
        COMPONENTS,

    ],
    providers: [AgentsService],
    exports: [
    ],
})
export class AgentsModule {}
