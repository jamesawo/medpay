import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Inject, OnDestroy, Optional } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { StartupService } from '@core';
import { ReuseTabService } from '@delon/abc/reuse-tab';
import { DA_SERVICE_TOKEN, ITokenService, SocialService } from '@delon/auth';
import { _HttpClient, MenuService, SettingsService, TitleService } from '@delon/theme';
import { NzTabChangeEvent } from 'ng-zorro-antd/tabs';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { finalize } from 'rxjs';

import { PassportService } from '../service/passport.service';
import { ACLService } from '@delon/acl';
import { LoginResponse } from '../data/user.payload';

@Component({
    selector: 'passport-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.less'],
    providers: [SocialService],
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class UserLoginComponent implements OnDestroy {
    public form = this.fb.group({
        userName: [null, [Validators.required]],
        password: [null, [Validators.required]],
        remember: [true],
    });
    public error = '';
    public errorList: string[] = [];
    public type = 0;
    public loading = false;

    // #region get captcha
    public count = 0;
    public interval$: any;

    constructor(
        private fb: FormBuilder,
        private router: Router,
        private settingsService: SettingsService,
        private socialService: SocialService,
        @Optional()
        @Inject(ReuseTabService)
        private reuseTabService: ReuseTabService,
        @Inject(DA_SERVICE_TOKEN) private tokenService: ITokenService,
        private startupSrv: StartupService,
        private http: _HttpClient,
        private cdr: ChangeDetectorRef,
        private passportService: PassportService,
        private toast: ToastrService,
        private spinner: NgxSpinnerService,
        private menuService: MenuService,
        private aclService: ACLService,
        private titleService: TitleService,
    ) {
    }

    ngOnDestroy(): void {
        if (this.interval$) {
            clearInterval(this.interval$);
        }
    }

    public switch({ index }: NzTabChangeEvent): void {
        this.type = index!;
    }


    public onSubmitForm(): void {
        this.error = '';
        if (this.type === 0) {
            const { userName, password } = this.form.controls;
            userName.markAsDirty();
            userName.updateValueAndValidity();
            password.markAsDirty();
            password.updateValueAndValidity();
            if (userName.invalid || password.invalid) {
                return;
            }
        }
        this.loading = true;
        this.cdr.detectChanges();

        const userName = this.form.value.userName ?? '';
        const password = this.form.value.password ?? '';

        this.passportService
            .login(userName, password)
            .pipe(
                finalize(() => {
                    this.loading = false;
                    this.cdr.detectChanges();
                }),
            )
            .subscribe({
                next: (res) => {
                    this.passportService.storeLoginResponse(res);
                    this.reuseTabService.clear();
                    this.setTokenAfterLoginSuccess(res);
                    this.setMenuAfterLoginSuccess(res);
                    this.setUserAfterLoginSuccess(res);
                    this.setAclAfterLoginSuccess(res);
                    this.redirectAfterLogin();
                },
                error: (e) => {
                    this.displayLoginError(e);
                    this.cdr.detectChanges();
                    return;
                },
                complete: () => {
                },
            });
    }

    private displayLoginError(e: any): void {
        const { message, error } = e.error;
        this.error = message;
        this.errorList = error;
        this.toast.error(message, 'Login Failed');
    }

    private setTokenAfterLoginSuccess(res: LoginResponse): void {
        const token = { token: res.token };
        this.tokenService.set(token);
    }

    private setUserAfterLoginSuccess(res: LoginResponse): void {
        const user: any = this.passportService.setUser(res);
        this.settingsService.setUser(user);
    }

    private setMenuAfterLoginSuccess(res: LoginResponse): void {
        this.passportService.setMenu(res);
    }

    private setAclAfterLoginSuccess(res: LoginResponse): void {
        this.aclService.setFull(true);
    }

    private setPageTitle(): void {
        const { name, description } = this.getAppDetails();
        this.titleService.suffix = name;
        this.titleService.setTitle(description);
    }

    private setAppDetails(): void {
        const details = this.getAppDetails();
        this.settingsService.setApp(details);
    }

    protected getAppDetails(): { name: string, description: string } {
        return {
            name: `Paymed v1`,
            description: `Cash Collection Service`,
        };
    }

    private redirectAfterLogin(): void {
        let url = this.tokenService.referrer!.url || '/';
        if (url.includes('/passport')) {
            url = '/';
        }
        this.router.navigateByUrl(url).then();
    }
}

