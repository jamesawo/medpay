import { ChangeDetectionStrategy, Component, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { DA_SERVICE_TOKEN, ITokenService } from '@delon/auth';
import { SettingsService, User } from '@delon/theme';
import { PassportService } from '../../../routes/passport/service/passport.service';

@Component({
    selector: 'header-user',
    template: `
        <div
            class="alain-default__nav-item d-flex align-items-center px-sm"
            nz-dropdown
            nzPlacement="bottomRight"
            [nzDropdownMenu]="userMenu"
        >
            <nz-avatar [nzSrc]="user.avatar" nzSize="small" class="mr-sm"></nz-avatar>
            {{ user.name }}
        </div>
        <nz-dropdown-menu #userMenu="nzDropdownMenu">
            <div nz-menu class="width-sm">
                <div nz-menu-item routerLink="/dashboard">
                    <i nz-icon nzType="user" class="mr-sm"></i>
                    Account Center
                </div>
                <div nz-menu-item routerLink="/dashboard">
                    <i nz-icon nzType="setting" class="mr-sm"></i>
                    Account Settings
                </div>

                <li nz-menu-divider></li>
                <div nz-menu-item (click)="logout()">
                    <i nz-icon nzType="logout" class="mr-sm"></i>
                    Logout
                </div>
            </div>
        </nz-dropdown-menu>
    `,
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HeaderUserComponent {
    get user(): User {
        return this.settings.user;
    }

    constructor(
        private settings: SettingsService,
        private router: Router,
        @Inject(DA_SERVICE_TOKEN) private tokenService: ITokenService,
        private passportService: PassportService
    ) {}

    logout(): void {
        this.tokenService.clear();
        this.passportService.removeLoginResponse();
        this.router.navigateByUrl(this.tokenService.login_url!).then();
    }
}
