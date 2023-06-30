import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { APP_INITIALIZER, LOCALE_ID, NgModule, Type } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NzMessageModule } from 'ng-zorro-antd/message';
import { NzNotificationModule } from 'ng-zorro-antd/notification';
import { Observable } from 'rxjs';

// #region default language
// Reference: https://ng-alain.com/docs/i18n
import en, { default as ngLang } from '@angular/common/locales/en';
import { DELON_LOCALE, en_US as delonLang } from '@delon/theme';
import { enUS, zhCN as dateLang } from 'date-fns/locale';
import { en_US, en_US as zorroLang, NZ_DATE_LOCALE, NZ_I18N } from 'ng-zorro-antd/i18n';
// register angular
import { registerLocaleData } from '@angular/common';
// #region Startup Service
import { StartupService } from '@core';
import { DelonAuthModule, JWTInterceptor } from '@delon/auth';
import { AppComponent } from './app.component';
import { CoreModule } from './core/core.module';
import { GlobalConfigModule } from './global-config.module';
import { LayoutModule } from './layout/layout.module';
import { RoutesModule } from './routes/routes.module';
import { SharedModule } from './shared/shared.module';
import { STWidgetModule } from './shared/st-widget/st-widget.module';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NgxSpinnerModule } from 'ngx-spinner';
import { ToastrModule } from 'ngx-toastr';

const LANG = {
    abbr: 'en',
    ng: ngLang,
    zorro: zorroLang,
    date: dateLang,
    delon: delonLang,
};

registerLocaleData(LANG.ng, LANG.abbr);
registerLocaleData(en);

const LANG_PROVIDES = [
    { provide: LOCALE_ID, useValue: LANG.abbr },
    { provide: NZ_I18N, useValue: LANG.zorro },
    { provide: NZ_DATE_LOCALE, useValue: LANG.date },
    { provide: DELON_LOCALE, useValue: LANG.delon },
    { provide: NZ_I18N, useValue: en_US },
    { provide: NZ_DATE_LOCALE, useValue: enUS },
];
// #endregion

const INTERCEPTOR_PROVIDES = [
    // { provide: HTTP_INTERCEPTORS, useClass: SimpleInterceptor, multi: true },
    // { provide: HTTP_INTERCEPTORS, useClass: DefaultInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: JWTInterceptor, multi: true },
];
// #endregion

// #region global third module
const GLOBAL_THIRD_MODULES: Array<Type<void>> = [NgxSpinnerModule];

// #endregion

export function StartupServiceFactory(startupService: StartupService): () => Observable<void> {
    return () => startupService.load();
}

const APPINIT_PROVIDES = [
    StartupService,
    {
        provide: APP_INITIALIZER,
        useFactory: StartupServiceFactory,
        deps: [StartupService],
        multi: true,
    },
];

// #endregion

@NgModule({
    declarations: [AppComponent],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        HttpClientModule,
        GlobalConfigModule.forRoot(),
        CoreModule,
        SharedModule,
        LayoutModule,
        RoutesModule,
        STWidgetModule,
        NzMessageModule,
        NzNotificationModule,
        NzIconModule,
        DelonAuthModule,
        ToastrModule.forRoot({
            enableHtml: true,
            preventDuplicates: true,
            positionClass: 'toast-top-center',
            timeOut: 7000,
        }),
        ...GLOBAL_THIRD_MODULES,
    ],
    providers: [...LANG_PROVIDES, ...INTERCEPTOR_PROVIDES, ...APPINIT_PROVIDES],
    bootstrap: [AppComponent],
})
export class AppModule {}
