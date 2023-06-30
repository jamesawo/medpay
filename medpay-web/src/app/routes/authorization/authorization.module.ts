import { NgModule, Type } from '@angular/core';
import { SharedModule } from '@shared';

import { AuthorizationRoutingModule } from './authorization-routing.module';
import { AuthorizationService } from './authorization.service';
import { AuthorizationPermissionComponent } from './roles/permission/permission.component';
import { AuthorizationRolesComponent } from './roles/roles.component';
import { AuthorizationUserComponent } from './users/user/user.component';
import { AuthorizationRoleCreateComponent } from './roles/role-create/authorization-role-create.component';
import { AuthorizationRoleManageComponent } from './roles/role-manage/authorization-role-manage.component';
import { RoleSearchDropdownComponent } from './components/role-search-dropdown/role-search-dropdown.component';
import { AuthorizationResetPasswordComponent } from './users/reset-password/authorization-reset-password.component';
import { AuthorizationUserPasswordResetComponent } from './user-password-reset/user-password-reset.component';

const COMPONENTS: Array<Type<void>> = [
    AuthorizationUserComponent,
    AuthorizationRolesComponent,
    AuthorizationRoleCreateComponent,
    AuthorizationRoleManageComponent,
    AuthorizationPermissionComponent,
    RoleSearchDropdownComponent,
    AuthorizationResetPasswordComponent
,
  AuthorizationUserPasswordResetComponent];

@NgModule({
    imports: [SharedModule, AuthorizationRoutingModule],
    declarations: COMPONENTS,
    providers: [AuthorizationService],
})
export class AuthorizationModule {}
