import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ServiceGroupCreateComponent } from './group/service-group-create/service-group-create.component';
import { ServiceGroupManageComponent } from './group/service-group-manage/service-group-manage.component';
import { RevenueHeadCreateComponent } from './revenue-head/revenue-head-create/revenue-head-create.component';
import { RevenueHeadManageComponent } from './revenue-head/revenue-head-manage/revenue-head-manage.component';
import { ServicesCreateComponent } from './services/services-create/services-create.component';
import { ServicesManageComponent } from './services/services-manage/services-manage.component';

const routes: Routes = [
    {
        path: 'revenue-head',
        children: [
            { path: 'create', component: RevenueHeadCreateComponent },
            { path: 'manage', component: RevenueHeadManageComponent },
        ],
    },
    {
        path: 'group',
        children: [
            { path: 'create', component: ServiceGroupCreateComponent },
            { path: 'manage', component: ServiceGroupManageComponent },
        ],
    },
    {
        path: 'services',
        children: [
            { path: 'create', component: ServicesCreateComponent },
            { path: 'manage', component: ServicesManageComponent },
        ],
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class ServiceGroupRoutingModule {}
