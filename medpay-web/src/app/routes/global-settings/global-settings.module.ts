import { NgModule, Type } from '@angular/core';
import { SharedModule } from '@shared';
import { GlobalSettingsRoutingModule } from './global-settings-routing.module';
import { GlobalSettingsHospitalSettingsComponent } from './hospital-settings/hospital-settings.component';
import { GlobalSettingsUsersSettingsComponent } from './users-settings/users-settings.component';
import { GlobalSettingsRolesPermSettingsComponent } from './roles-perm-settings/roles-perm-settings.component';
import { GlobalSettingsTransactionSettingsComponent } from './transaction-settings/transaction-settings.component';
import { GlobalSettingsHospitalSettingsDetailsComponent } from './hospital-settings-details/global-settings-hospital-settings-details.component';
import { GlobalHospitalApiSettingsComponent } from './components/global-hospital-api-settings/global-hospital-api-settings.component';
import { GlobalHospitalBasicDetailsSettingsComponent } from './components/global-hospital-basic-details-settings/global-hospital-basic-details-settings.component';
import { GlobalHospitalCollectionModeSettingsComponent } from './components/global-hospital-collection-mode-settings/global-hospital-collection-mode-settings.component';

const COMPONENTS: Type<void>[] = [
    GlobalSettingsHospitalSettingsComponent,
    GlobalSettingsHospitalSettingsDetailsComponent,
    GlobalSettingsUsersSettingsComponent,
    GlobalSettingsRolesPermSettingsComponent,
    GlobalSettingsTransactionSettingsComponent,
];

@NgModule({
    imports: [
        SharedModule,
        GlobalSettingsRoutingModule,
    ],
    declarations: [
        COMPONENTS,
        GlobalHospitalApiSettingsComponent,
        GlobalHospitalBasicDetailsSettingsComponent,
        GlobalHospitalCollectionModeSettingsComponent,
    ],
})
export class GlobalSettingsModule {
}
