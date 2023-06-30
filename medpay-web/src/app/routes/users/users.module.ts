import { NgModule, Type } from '@angular/core';
import { SharedModule } from '@shared';

import { UsersCreateUsersComponent } from './create-users/create-users.component';
import { UsersUsersReportComponent } from './users-report/users-report.component';
import { UsersRoutingModule } from './users-routing.module';
import { UsersService } from './users.service';
import { UsersViewUsersComponent } from './view-users/view-users.component';
import { CreateUserPageOneComponent } from './create-users/components/create-user-page-one/create-user-page-one.component';
import { CreateUserPageTwoComponent } from './create-users/components/create-user-page-two/create-user-page-two.component';
import { CreateUserPageThreeComponent } from './create-users/components/create-user-page-three/create-user-page-three.component';
import { UserGenderDropdownComponent } from './components/user-gender-dropdown/user-gender-dropdown.component';
import { UserLimitTypeDropdownComponent } from './components/user-limit-type-dropdown/user-limit-type-dropdown.component';
import { UserPreferredNameDropDownComponent } from './components/user-preferred-name/user-preferred-name-drop-down.component';

const COMPONENTS: Array<Type<void>> = [
    UsersViewUsersComponent,
    UsersCreateUsersComponent,
    UsersUsersReportComponent,
    CreateUserPageOneComponent,
    CreateUserPageTwoComponent,
    CreateUserPageThreeComponent,
    UserGenderDropdownComponent,
    UserLimitTypeDropdownComponent,
    UserPreferredNameDropDownComponent
];

@NgModule({
    imports: [SharedModule, UsersRoutingModule],
    declarations: [
        COMPONENTS,

    ],
    providers: [UsersService],
    exports: [
    ],
})
export class UsersModule {}
