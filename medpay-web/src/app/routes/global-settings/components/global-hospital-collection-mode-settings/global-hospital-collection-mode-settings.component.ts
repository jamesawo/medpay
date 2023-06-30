import { ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { HospitalService } from '../../../hospital/hospital.service';
import { ToastrService } from 'ngx-toastr';
import { NzMessageService } from 'ng-zorro-antd/message';
import { HospitalPayload } from '../../../hospital/data/hospital.payload';
import { HospitalCollectionEnum } from '../../../hospital/data/hospital.enum';
import { displayError, validateFormControls } from '@shared';

@Component({
    selector: 'app-global-hospital-collection-mode-settings',
    templateUrl: './global-hospital-collection-mode-settings.component.html',
    styles: [],
})
export class GlobalHospitalCollectionModeSettingsComponent implements OnInit {

    @Input()
    public hospital?: HospitalPayload;

    @Output()
    public hospitalChange = new EventEmitter<HospitalPayload>();

    public form: FormGroup = new FormGroup({});
    public isLoading = false;
    public hasCollectionModeError = false;
    public hasActiveStatusError = false;
    public hasUseSoftwareError = false;
    public hasExpiryDateError = false;

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
        this.form.reset();
        this.form = this.fb.group({
            collectionMode: [this.hospital?.collectionModelEnum, [Validators.required]],
            expiryDate: [this.hospital?.expiryDate, Validators.required],
            isEnabled: [this.hospital?.isEnabled, [Validators.required]],
            hospitalHasSoftware: [this.hospital?.hasHospitalSoftware, [Validators.required]],
        });
    }

    public submitForm() {
        const isInvalid: boolean = validateFormControls(this.form);
        if (isInvalid) {
            this.mgService.info('Some form fields are invalid');
            return;
        }
        this.isLoading = true;
        this.sub.add(
            this.hospitalService.updateHospitalPartialProps(this.hospital?.id, this.form.value).subscribe({
                next: (res) => {
                    this.isLoading = false;
                    if (!res){
                        this.toaster.error('Update Failed');
                        this.cdr.detectChanges();
                        return;
                    }
                    this.toaster.success('Updated Successfully');
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

    public onCollectionModelSelected(value: HospitalCollectionEnum) {
        this.form.controls['collectionMode'].setValue(value);
        if (value) {
            this.hasCollectionModeError = false;
        }
    }

    public onExpiryDateSelected(date?: string) {
        this.form.controls['expiryDate'].setValue(date);
        if (date){
            this.hasExpiryDateError = false;
        }
    }

    public onIsEnabledChanged(value: boolean) {
        this.form.controls['isEnabled'].setValue(value);
        this.hasActiveStatusError = false;
    }

    public onUseSoftwareChanged(value: boolean) {
        this.form.controls['hospitalHasSoftware'].setValue(value);
        this.hasActiveStatusError = false;
    }
}
