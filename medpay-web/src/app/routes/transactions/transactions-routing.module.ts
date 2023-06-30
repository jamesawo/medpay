import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { TransactionsCheckPaymentStatusComponent } from './check-payment-status/check-payment-status.component';
import { TransactionsPaymentDetailsComponent } from './payment-details/payment-details.component';
import { TransactionsReconcilePaymentComponent } from './reconcile-payment/reconcile-payment.component';
import { TransactionsReprintReceiptComponent } from './reprint-receipt/reprint-receipt.component';
import { TransactionsTransactionReportComponent } from './transaction-report/transaction-report.component';
import { TransactionsViewTransactionsComponent } from './view-transactions/view-transactions.component';

const routes: Routes = [
    { path: 'view-transactions', component: TransactionsViewTransactionsComponent },
    { path: 'check-payment-status', component: TransactionsCheckPaymentStatusComponent },
    { path: 'payment-details', component: TransactionsPaymentDetailsComponent },
    { path: 'reconcile-payment', component: TransactionsReconcilePaymentComponent },
    { path: 'reprint-receipt', component: TransactionsReprintReceiptComponent },
    { path: 'transaction-report', component: TransactionsTransactionReportComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class TransactionsRoutingModule {}
