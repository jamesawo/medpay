import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { GlobalSettingsHospitalSettingsComponent } from './hospital-settings/hospital-settings.component';
import { GlobalSettingsRolesPermSettingsComponent } from './roles-perm-settings/roles-perm-settings.component';
import { GlobalSettingsTransactionSettingsComponent } from './transaction-settings/transaction-settings.component';
import { GlobalSettingsUsersSettingsComponent } from './users-settings/users-settings.component';
import { GlobalSettingsHospitalSettingsDetailsComponent } from './hospital-settings-details/global-settings-hospital-settings-details.component';

const routes: Routes = [
    { path: 'hospital-settings', component: GlobalSettingsHospitalSettingsComponent },
    { path: 'hospital-settings-details', component: GlobalSettingsHospitalSettingsDetailsComponent, data: {id: 1} },
    { path: 'users-settings', component: GlobalSettingsUsersSettingsComponent },
    { path: 'roles-perm-settings', component: GlobalSettingsRolesPermSettingsComponent },
    { path: 'transaction-settings', component: GlobalSettingsTransactionSettingsComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class GlobalSettingsRoutingModule {}
