import { HttpClient } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ACLService } from '@delon/acl';
import { DA_SERVICE_TOKEN, ITokenService } from '@delon/auth';
import { MenuService, SettingsService, TitleService } from '@delon/theme';
import type { NzSafeAny } from 'ng-zorro-antd/core/types';
import { NzIconService } from 'ng-zorro-antd/icon';
import { catchError, map, Observable, of } from 'rxjs';

import { ICONS } from '../../../style-icons';
import { ICONS_AUTO } from '../../../style-icons-auto';
import { PassportService } from '../../routes/passport/service/passport.service';

/**
 * Used for application startup
 * Generally used to get the basic data of the application, like: Menu Data, User Data, etc.
 */
@Injectable()
export class StartupService {
    constructor(
        iconSrv: NzIconService,
        private menuService: MenuService,
        private settingService: SettingsService,
        private aclService: ACLService,
        private titleService: TitleService,
        @Inject(DA_SERVICE_TOKEN) private tokenService: ITokenService,
        private httpClient: HttpClient,
        private router: Router,
        private passportService: PassportService,
    ) {
        iconSrv.addIcon(...ICONS_AUTO, ...ICONS);
    }

    private viaHttp(): Observable<void> {
        return this.httpClient.get('assets/tmp/app-data.json').pipe(
            catchError((res: NzSafeAny) => {
                console.warn(`StartupService.load: Network request failed`, res);
                setTimeout(() => this.router.navigateByUrl(`/exception/500`));
                return of({});
            }),
            map((res: NzSafeAny) => {
                // Application information: including site name, description, year
                this.settingService.setApp(res.app);
                // User information: including name, avatar, email address
                this.settingService.setUser(res.user);
                // ACL: Set the permissions to full, https://ng-alain.com/acl/getting-started
                this.aclService.setFull(true);
                // Menu data, https://ng-alain.com/theme/menu
                this.menuService.add(res.menu);
                // Can be set page suffix title, https://ng-alain.com/theme/title
                this.titleService.suffix = res.app.name;
            }),
        );
    }

    private viaMock(): Observable<void> {
        const tokenData = this.tokenService.get();
        if (!tokenData?.token) {
            this.router.navigateByUrl(this.tokenService.login_url!).then();
            return of();
        }
        let loginResponse = this.passportService.getLoginResponse();
        if (!loginResponse) {
            this.router.navigateByUrl(this.tokenService.login_url!).then();
            return of();
        }
        // mock
        const app: any = this.passportService.getAppDetails();
        const user: any = this.passportService.setUser(loginResponse);

        // Application information: including site name, description, year
        this.settingService.setApp(app);

        // User information: including name, avatar, email address
        this.settingService.setUser(user);

        // ACL: Set the permissions to full, https://ng-alain.com/acl/getting-started
        this.aclService.setFull(true);

        // Menu data, https://ng-alain.com/theme/menu
        this.passportService.setMenu(loginResponse);

        // Can be set page suffix title, https://ng-alain.com/theme/title
        this.titleService.suffix = app.name;
        return of();
    }

    load(): Observable<void> {
        // http
        // return this.viaHttp();
        // Don't use this in a production environment, viaMock is just to simulate some data to make the scaffolding work properly at first
        return this.viaMock();
    }
}
