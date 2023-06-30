import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd/message';
import { ToastrService } from 'ngx-toastr';
import { HospitalPayload } from '../../../hospital/data/hospital.payload';
import { displayError, validateFormControls } from '@shared';
import { ServiceGroupService } from '../../service-group.service';
import { Subscription } from 'rxjs';
import { HospitalSearchComponent } from '../../../hospital/components/hospital-search/hospital-search.component';


@Component({
    selector: 'app-service-group-create',
    templateUrl: './service-group-create.component.html',
    styles: [],
})
export class ServiceGroupCreateComponent implements OnInit, OnDestroy {
    @ViewChild('hospitalSearchComponent')
    public hospitalSearchComponent?: HospitalSearchComponent;
    public isLoading = false;
    public form: FormGroup = this.fb.group({});
    private sub = new Subscription();

    constructor(
        private fb: FormBuilder,
        private msg: NzMessageService,
        private toaster: ToastrService,
        private service: ServiceGroupService,
    ) {

    }

    ngOnInit(): void {
        this.setUp();
    }

    ngOnDestroy(): void {
        this.sub.unsubscribe();
    }

    public get hospital() {
        return this.form.get('hospital');
    }

    public set isEnabled(value: boolean) {
        this.form.controls['isEnabled'].setValue(value);
    }

    public get isEnabled(): boolean {
        return this.form.controls['isEnabled'].value;
    }

    private setUp(): void {
        this.form = this.fb.group({
            hospital: [null, [Validators.required]],
            title: ['', [Validators.required, Validators.min(4)]],
            isEnabled: [true, [Validators.required]],
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
            this.service.updateServiceGroup(this.form.value).subscribe({
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
        this.hospitalSearchComponent?.onClear();
        this.setUp();
    }

}
