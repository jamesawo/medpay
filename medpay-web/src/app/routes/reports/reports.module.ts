import { NgModule, Type } from '@angular/core';
import { SharedModule } from '@shared';
import { ReportsRoutingModule } from './reports-routing.module';
import { ReportsService } from './reports.service';
import { ReportAgentCollectionComponent } from './report-agent-collection/report-agent-collection.component';
import { ReportDailyCollectionComponent } from './report-daily-collection/report-daily-collection.component';
import { ServiceGroupModule } from '../service-group/service-group.module';


const COMPONENTS: Array<Type<void>> = [
    ReportAgentCollectionComponent,
    ReportDailyCollectionComponent
];

@NgModule({
    imports: [SharedModule, ReportsRoutingModule, ServiceGroupModule],
    declarations: [
        COMPONENTS,

    ],
    providers: [ReportsService],
    exports: [
    ],
})
export class ReportsModule{}