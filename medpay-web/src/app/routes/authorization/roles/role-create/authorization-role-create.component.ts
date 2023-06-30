import { Component, OnDestroy, OnInit } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd/message';
import { ToastrService } from 'ngx-toastr';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { HospitalPayload } from '../../../hospital/data/hospital.payload';
import { displayError, validateFormControls } from '@shared';
import { AuthorizationService } from '../../authorization.service';

@Component({
    selector: 'app-role-create',
    templateUrl: './authorization-role-create.component.html',
    styles: [],
})
export class AuthorizationRoleCreateComponent implements OnInit, OnDestroy {
    public form: FormGroup = this.fb.group({});
    public isLoading = false;

    private sub = new Subscription();

    constructor(
        private msg: NzMessageService,
        private toaster: ToastrService,
        private fb: FormBuilder,
        private service: AuthorizationService,
    ) {
    }

    ngOnInit(): void {
        this.setUp();
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }


    public set status(value: boolean) {
        this.form.controls['status'].setValue(value);
    }

    public get status(): boolean {
        return this.form.controls['status'].value;
    }

    private setUp(): void {
        this.form = this.fb.group({
            name: ['', [Validators.required, Validators.min(4)]],
            description: ['', [Validators.required, Validators.min(4)]],
            status: [true, [Validators.required]],
        });
    }

    public onHospitalSelected(hospitalPayload: HospitalPayload): void {
        this.form.controls['hospital'].setValue(hospitalPayload);
    }

    public onToggleIsEnabled(value: boolean): void {
        this.form.controls['isEnabled'].setValue(value);
    }

    public submit(): void {
        let isInvalid = validateFormControls(this.form);
        if (isInvalid) {
            this.msg.error('Some fields are invalid', { nzAnimate: true });
            return;
        }
        this.isLoading = true;
        this.msg.loading('submitting...');
        this.sub.add(
            this.service.createRole(this.form.value).subscribe({
                next: (res) => {
                    this.isLoading = false;
                    if (res) {
                        this.toaster.success('Created Successfully.');
                        this.resetPayload();
                    }
                },
                error: (err) => {
                    this.isLoading = false;
                    displayError(err, { toastService: this.toaster });
                },
            }),
        );

    }

    public resetPayload(): void {
        this.form.reset();
        this.setUp();
    }

}
