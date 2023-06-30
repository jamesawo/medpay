import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ReportDailyCollectionComponent } from './report-daily-collection/report-daily-collection.component';
import { ReportAgentCollectionComponent } from './report-agent-collection/report-agent-collection.component';


const routes: Routes = [
    { path: 'daily-collection', component: ReportDailyCollectionComponent },
    { path: 'agent-collection', component: ReportAgentCollectionComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export  class ReportsRoutingModule {}