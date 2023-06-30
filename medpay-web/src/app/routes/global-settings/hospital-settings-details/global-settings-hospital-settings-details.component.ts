import { ChangeDetectorRef, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { NzTabPosition, NzTabsCanDeactivateFn } from 'ng-zorro-antd/tabs';
import { ActivatedRoute, Router } from '@angular/router';
import { HospitalCollectionEnum } from '../../hospital/data/hospital.enum';
import { Observable, Subscription } from 'rxjs';
import { NzModalService } from 'ng-zorro-antd/modal';
import { GlobalHospitalBasicDetailsSettingsComponent } from '../components/global-hospital-basic-details-settings/global-hospital-basic-details-settings.component';
import { BreakPoints, ResponsiveService } from '../../../shared/utils/responsive.service';
import {
    HospitalApiConfigurationPayload,
    HospitalBasicDetailPayload,
    HospitalPayload,
} from '../../hospital/data/hospital.payload';
import { splitEnum } from '@shared';

@Component({
    selector: 'app-hospital-settings-details',
    templateUrl: './global-settings-hospital-settings-details.component.html',
    styles: [
        `
            .content-heading {
                margin-top: 10px;
                width: fit-content;
            }

            .content-title {
                font-weight: 800;
            }

            .support-heading {
                margin-top: 30px;
            }

            .support-heading h3 {
                font-weight: 600;

            }

            .support-item {
                margin-right: 20px;
                letter-spacing: 0.5px;
            }

            .support-item-info {
                margin-left: 10px;
            }

            .support-item-wrapper {
                display: flex;
                flex-direction: row;
                flex-wrap: wrap;
            }
        `,
    ],
})
export class GlobalSettingsHospitalSettingsDetailsComponent implements OnInit, OnDestroy {
    @ViewChild('basicDetailsSettingsComponent')
    public basicDetailsSettingsComponent?: GlobalHospitalBasicDetailsSettingsComponent;

    @ViewChild('apiSettingsComponent')
    public apiSettingsComponent?: GlobalHospitalBasicDetailsSettingsComponent;

    public position: NzTabPosition = 'left';
    public hospital?: HospitalPayload; // passed down through the routes state
    public manual = HospitalCollectionEnum.MANUAL;
    public gateway = HospitalCollectionEnum.GATEWAY;
    public breakpoint = '';

    private subscription: Subscription = new Subscription();

    constructor(
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private modal: NzModalService,
        private responsiveService: ResponsiveService,
        private cdr: ChangeDetectorRef
    ) {
        const state: any = router.getCurrentNavigation()?.extras.state;

        if (state?.data) {
            this.hospital = state?.data;
        }
        else {
            this.goToSettingPage();
        }
    }

    ngOnInit(): void {
        this.observeBreakpoint();
        if (!this.hospital) {
            this.goToSettingPage();
        }
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    private goToSettingPage() {
        this.router.navigateByUrl('/global-settings/hospital-settings').then();
    }

    public canDeactivate: NzTabsCanDeactivateFn = (fromIndex: number, toIndex: number) => {
        switch (fromIndex) {
            case 0:
                return toIndex === 1;
            case 1:
                return Promise.resolve(toIndex === 2);
            case 2:
                return this.confirm();
            default:
                return true;
        }

    };
    isImageLoaded = false;

    private confirm(): Observable<boolean> {
        return new Observable(observer => {
            this.modal.confirm({
                nzTitle: 'Unsaved changes, Are you sure you want to leave?',
                nzOnOk: () => {
                    observer.next(true);
                    observer.complete();
                },
                nzOnCancel: () => {
                    observer.next(false);
                    observer.complete();
                },
            });
        });
    }

    private observeBreakpoint() {
        this.subscription.add(
            this.responsiveService.mediaBreakpoint$.subscribe(value => {
                this.position = value === BreakPoints.XS || value === BreakPoints.MD ? 'top' : 'left';
            }),
        );
    }

    public onUpdated(basicDetails: HospitalBasicDetailPayload) {
        if (this.hospital) {
            this.hospital.detail = basicDetails;
        }
    }

    public stripText(text?: string): string {
        return splitEnum(text ?? '');
    }

    public onImageLoaded(event: Event) {
        this.isImageLoaded = true;
    }

    public onUpdateApi(value: HospitalApiConfigurationPayload) {
        if (this.hospital){
            this.hospital.apiConfiguration = value;
        }
    }

    public updatePartial(payload: HospitalPayload) {
        if (payload && this.hospital) {
            this.hospital.hasHospitalSoftware = payload.hasHospitalSoftware;
            this.hospital.expiryDate = payload.expiryDate;
            this.hospital.isEnabled = payload.isEnabled;
            this.hospital.collectionModelEnum = payload.collectionModelEnum;
            this.cdr.detectChanges();
        }
    }
}
