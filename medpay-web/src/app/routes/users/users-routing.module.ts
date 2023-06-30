import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UsersCreateUsersComponent } from './create-users/create-users.component';
import { UsersUsersReportComponent } from './users-report/users-report.component';
import { UsersViewUsersComponent } from './view-users/view-users.component';

const routes: Routes = [
    { path: 'view-users', component: UsersViewUsersComponent },
    { path: 'create-users', component: UsersCreateUsersComponent },
    { path: 'users-report', component: UsersUsersReportComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class UsersRoutingModule {}
