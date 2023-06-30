import { NgModule, Type } from '@angular/core';
import { SharedModule } from '@shared';

import { ServiceGroupCreateComponent } from './group/service-group-create/service-group-create.component';
import { ServiceGroupManageComponent } from './group/service-group-manage/service-group-manage.component';
import { ServiceGroupRoutingModule } from './service-group-routing.module';
import { ServiceGroupService } from './service-group.service';
import { RevenueHeadManageComponent } from './revenue-head/revenue-head-manage/revenue-head-manage.component';
import { ServicesCreateComponent } from './services/services-create/services-create.component';
import { ServicesManageComponent } from './services/services-manage/services-manage.component';
import { RevenueHeadCreateComponent } from './revenue-head/revenue-head-create/revenue-head-create.component';
import { ServiceGroupSearchComponent } from './components/service-group-search/service-group-search.component';
import { RevenueHeadSearchComponent } from './components/revenue-head-search/revenue-head-search.component';
import { ServiceSearchComponent } from './components/service-search/service-search.component';

const COMPONENTS: Array<Type<void>> = [
    ServiceGroupCreateComponent,
    ServiceGroupManageComponent,
    RevenueHeadCreateComponent,
    RevenueHeadManageComponent,
    ServicesCreateComponent,
    ServicesManageComponent,
    ServiceGroupSearchComponent,
    RevenueHeadSearchComponent,
    ServiceSearchComponent,
    ServiceSearchComponent

];

@NgModule({
    imports: [SharedModule, ServiceGroupRoutingModule],
    declarations: [
        COMPONENTS,
    ],
    providers: [ServiceGroupService],
    exports: [
        ServiceGroupSearchComponent,
        RevenueHeadSearchComponent,
        ServiceSearchComponent,
    ],
})
export class ServiceGroupModule {}
