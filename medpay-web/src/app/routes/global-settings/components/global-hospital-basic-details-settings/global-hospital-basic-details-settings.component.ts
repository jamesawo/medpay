import { ChangeDetectorRef, Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HospitalBasicDetailPayload } from '../../../hospital/data/hospital.payload';
import { HospitalSupportChannelEnum } from '../../../hospital/data/hospital.enum';
import { HospitalService } from '../../../hospital/hospital.service';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { displayError, validateFormControls } from '@shared';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
    selector: 'app-global-hospital-basic-details-settings',
    templateUrl: './global-hospital-basic-details-settings.component.html',
    styles: [],
})
export class GlobalHospitalBasicDetailsSettingsComponent implements OnInit, OnDestroy {

    @Input()
    public payload?: {id?: number, data?: HospitalBasicDetailPayload };

    @Output()
    public payloadChange = new EventEmitter();

    public form: FormGroup = new FormGroup({});
    public isLoading = false;
    public hasPendingUpdate = false;

    private sub: Subscription = new Subscription();

    constructor(
        private fb: FormBuilder,
        private hospitalService: HospitalService,
        private toaster: ToastrService,
        private mgService: NzMessageService,
        private cdr: ChangeDetectorRef,
    ) {}

    ngOnInit(): void {
        this.setForm();
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    public setForm(): void {
        this.form.reset();
        this.form = this.fb.group({
            name: [this.payload?.data?.name, [Validators.required]],
            hospitalLogoUrl: [this.payload?.data?.hospitalLogoUrl],
            supportEmail: [this.payload?.data?.supportEmail, [Validators.required]],
            supportName: [this.payload?.data?.supportName, [Validators.required]],
            supportPhone: [this.payload?.data?.supportPhone, [Validators.required]],
            supportChannel: [this.payload?.data?.supportChannel],
            hospitalAddress: [this.payload?.data?.hospitalAddress, [Validators.required]],
        });
    }

    public hasUnsavedChanges(): boolean {
        let isFormDirty = this.form.dirty;
        return isFormDirty && !this.hasPendingUpdate;
    }

    public onSubmitForm() {
        const isInvalid: boolean = validateFormControls(this.form);
        if (isInvalid) {
            this.mgService.info('Some form fields are invalid');
            this.hasPendingUpdate = true;
            return;
        }
        this.isLoading = true;
        const hospitalId = this.payload?.id ?? 0;
        const payload: HospitalBasicDetailPayload = this.form.value;
        this.sub.add(
            this.hospitalService.onUpdateBasicDetails(hospitalId, payload).subscribe({
                next: (res) => {
                    this.isLoading = false;
                    if (!res){
                        this.toaster.error('Update Failed');
                        this.cdr.detectChanges();
                        return;
                    }
                    this.setPayAfterUpdateSuccessful(res);
                    return;
                },
                error: (err) => {
                    this.isLoading = false;
                    displayError(err, {toastService: this.toaster});
                    this.cdr.detectChanges();
                },
            }),
        );
    }

    public onSupportChannelChanged(value: HospitalSupportChannelEnum) {
        this.form.value.supportChannel = value;
    }

    private onValidateForm() {
        let isFormInvalid = this.form.invalid;
        if (isFormInvalid) {
            Object.keys(this.form.controls).forEach(key => {
                if (key) {
                    this.form.get(`${key}`)?.markAsDirty();
                    this.form.get(`${key}`)?.updateValueAndValidity();
                }
            });
        }
        return isFormInvalid;
    }

    private setPayAfterUpdateSuccessful(res: HospitalBasicDetailPayload) {
        if (this.payload) {
            this.payload.data = res;
            this.payloadChange.emit(res);
            this.hasPendingUpdate = false;
            this.toaster.success('Updated Successfully',);
            this.setForm();
            this.cdr.detectChanges();
        }
    }
}