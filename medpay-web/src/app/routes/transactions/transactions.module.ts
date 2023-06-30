import { NgModule, Type } from '@angular/core';
import { SharedModule } from '@shared';

import { TransactionsCheckPaymentStatusComponent } from './check-payment-status/check-payment-status.component';
import { TransactionsPaymentDetailsComponent } from './payment-details/payment-details.component';
import { TransactionsReconcilePaymentComponent } from './reconcile-payment/reconcile-payment.component';
import { TransactionsReprintReceiptComponent } from './reprint-receipt/reprint-receipt.component';
import { TransactionsTransactionReportComponent } from './transaction-report/transaction-report.component';
import { TransactionsRoutingModule } from './transactions-routing.module';
import { TransactionsService } from './transactions.service';
import { TransactionsViewTransactionsComponent } from './view-transactions/view-transactions.component';
import { TransactionStatusDropdownComponent } from './components/transaction-status-dropdown/transaction-status-dropdown.component';

const COMPONENTS: Array<Type<void>> = [
    TransactionsViewTransactionsComponent,
    TransactionsCheckPaymentStatusComponent,
    TransactionsReconcilePaymentComponent,
    TransactionsReprintReceiptComponent,
    TransactionsTransactionReportComponent,
    TransactionStatusDropdownComponent,
    TransactionsPaymentDetailsComponent
];

@NgModule({
    imports: [SharedModule, TransactionsRoutingModule],
    declarations: COMPONENTS,
    providers: [TransactionsService],
    exports: [
        TransactionsReprintReceiptComponent,
    ],
})
export class TransactionsModule {}
