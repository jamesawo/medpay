import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthorizationUserComponent } from './users/user/user.component';
import { AuthorizationRoleManageComponent } from './roles/role-manage/authorization-role-manage.component';
import { AuthorizationRoleCreateComponent } from './roles/role-create/authorization-role-create.component';
import { AuthorizationPermissionComponent } from './roles/permission/permission.component';
import { AuthorizationResetPasswordComponent } from './users/reset-password/authorization-reset-password.component';

const routes: Routes = [
    {
        path: 'users', children: [
            { path: 'password', component: AuthorizationResetPasswordComponent },
            { path: 'assign-role', component: AuthorizationUserComponent },
        ],
    },
    {
        path: 'roles', children: [
            { path: 'create', component: AuthorizationRoleCreateComponent },
            { path: 'manage', component: AuthorizationRoleManageComponent },
            { path: 'permissions', component: AuthorizationPermissionComponent },
        ],
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class AuthorizationRoutingModule {
}
