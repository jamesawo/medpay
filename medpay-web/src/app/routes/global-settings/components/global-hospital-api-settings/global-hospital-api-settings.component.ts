import { ChangeDetectorRef, Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { HospitalApiConfigurationPayload } from '../../../hospital/data/hospital.payload';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { HospitalService } from '../../../hospital/hospital.service';
import { ToastrService } from 'ngx-toastr';
import { NzMessageService } from 'ng-zorro-antd/message';
import { HospitalEnvironmentEnum } from '../../../hospital/data/hospital.enum';
import { displayError, validateFormControls } from '@shared';

@Component({
    selector: 'app-global-hospital-api-settings',
    templateUrl: './global-hospital-api-settings.component.html',
    styles: [``],
})
export class GlobalHospitalApiSettingsComponent implements OnInit, OnDestroy {
    public form: FormGroup = new FormGroup({});
    public isLoading = false;
    public hasPendingUpdate = false;
    public isInvalidAuthType = false;
    public isInvalidEnvironment = false;
    public method: string ='';

    @Input()
    public payload?: { id?: number, data?: HospitalApiConfigurationPayload };

    @Output()
    public payloadChange = new EventEmitter();
    public liveEnv: HospitalEnvironmentEnum = HospitalEnvironmentEnum.LIVE;

    private sub: Subscription = new Subscription();

    constructor(
        private fb: FormBuilder,
        private hospitalService: HospitalService,
        private toaster: ToastrService,
        private mgService: NzMessageService,
        private cdr: ChangeDetectorRef,
    ) {
    }

    ngOnInit(): void {
        this.setForm();
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    public setForm(): void {
        this.form = this.fb.group({
            apiBaseUrl: [this.payload?.data?.apiBaseUrl, [Validators.required]],
            apiTestBaseUrl: [this.payload?.data?.apiTestBaseUrl, [Validators.required]],
            authenticationType: [this.payload?.data?.authenticationType, [Validators.required]],
            publicToken: [this.payload?.data?.publicToken],
            privateToken: [this.payload?.data?.privateToken],
            apiKey: [this.payload?.data?.apiKey],
            oauthToken: [this.payload?.data?.oauthToken],
            environmentEnum: [this.payload?.data?.environmentEnum, [Validators.required]],
            searchBillMethod: [this.payload?.data?.searchBillMethod, [Validators.required]],
            payBillMethod: [this.payload?.data?.payBillMethod, [Validators.required]],
            searchBillEndpoint: [this.payload?.data?.searchBillEndpoint, [Validators.required]],
            payBillEndpoint: [this.payload?.data?.payBillEndpoint, [Validators.required]],
        });
    }

    public onSubmitForm(): void {
        const isInvalid: boolean = validateFormControls(this.form);
        this.isInvalidAuthType = this.form.dirty && this.form.controls['authenticationType'].invalid;
        this.isInvalidEnvironment = this.form.dirty && this.form.controls['environmentEnum'].invalid;
        if (isInvalid) {
            return;
        }

        this.isLoading = true;
        const hospitalId = this.payload?.id ?? 0;
        this.sub.add(
            this.hospitalService.updateApiConfiguration(hospitalId, this.form.value).subscribe({
                next: (res) => {
                    this.isLoading = false;
                    if (res) {
                        this.toaster.success('Updated Successfully');
                        this.payloadChange.emit(res);
                    } else {
                        this.toaster.error('Update Failed');
                    }
                },
                error: (err) => {
                    this.isLoading = false;
                    displayError(err, { toastService: this.toaster });
                },
            }),
        );

    }


    public onAuthTypeSelected(value: any) {
        if (value) {
            this.form.controls['authenticationType'].setValue(value);
            this.isInvalidAuthType = false;
            this.cdr.detectChanges();
        }
    }

    public onEnvironmentSelected(value: HospitalEnvironmentEnum) {
        if (value) {
            this.form.controls['environmentEnum'].setValue(value);
            this.isInvalidEnvironment = false;
            this.cdr.detectChanges();
        }
    }

}
