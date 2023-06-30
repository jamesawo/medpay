import { AuthModuleEnum } from './authorization.enum';
import { UserPayload } from '../../users/_data/user.payload';

export class PermissionPayload {
    id?: number;
    name: string = '';
    module?: AuthModuleEnum;
    description: string = '';
}

export class RolePayload {
    id?: number;
    string = '';
    name: string = '';
    description: string = '';
    status: boolean = true;
    permissions?: Set<PermissionPayload>;
    createdAt: string = '';

    constructor(name: string){
        this.name = name;
    }
}

export class RoleSearchPayload {
    name: string = '';
}

export class PermissionListPayload {
    module?: AuthModuleEnum;
    permissions: PermissionPayload[] = [];
}

export class ResetPasswordPayload {
    user?: UserPayload;
    newPassword: string = '';
    confirmPassword: string = ''
}