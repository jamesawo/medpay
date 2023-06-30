import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { HospitalSearchComponent } from '../../../hospital/components/hospital-search/hospital-search.component';
import { ServiceGroupSearchComponent } from '../../components/service-group-search/service-group-search.component';
import { RevenueHeadCategoryDropdownComponent } from '../../components/revenue-head-category-dropdown/revenue-head-category-dropdown.component';
import { RevenueHeadPayload, ServiceGroupPayload, ServicePayload } from '../../_data/service-group.payload';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { NzMessageService } from 'ng-zorro-antd/message';
import { ToastrService } from 'ngx-toastr';
import { ServiceGroupService } from '../../service-group.service';
import { HospitalPayload } from '../../../hospital/data/hospital.payload';
import { displayError, isFormControlInvalid, validateFormControls } from '@shared';
import { RevenueHeadSearchComponent } from '../../components/revenue-head-search/revenue-head-search.component';

@Component({
    selector: 'app-services-create',
    templateUrl: './services-create.component.html',
    styles: [],
})
export class ServicesCreateComponent implements OnInit, OnDestroy {
    @ViewChild('hospitalSearchComponent')
    public hospitalSearchComponent?: HospitalSearchComponent;
    @ViewChild('serviceGroupSearchComponent')
    public serviceGroupSearchComponent?: ServiceGroupSearchComponent;
    @ViewChild('revenueHeadCategoryDropdownComponent')
    public revenueHeadCategoryDropdownComponent?: RevenueHeadCategoryDropdownComponent;
    @ViewChild('revenueHeadSearchComponent')
    public revenueHeadSearchComponent?: RevenueHeadSearchComponent;

    public servicePayload: ServicePayload = new ServicePayload();
    public form: FormGroup = this.fb.group({});
    public isServiceGroupInvalid = false;
    public isHospitalInvalid = false;
    public isCategoryInvalid = false;
    public isRevenueHeadInvalid = false;
    public isLoading = false;

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

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    public set isEnabled(value: boolean) {
        this.form.controls['isEnabled'].setValue(value);
    }

    public get isEnabled(): boolean {
        return this.form.controls['isEnabled'].value;
    }

    public set revenueHead(value: RevenueHeadPayload) {
        this.form.controls['revenueHead'].setValue(value);
        if (value && value.id) {
            this.isRevenueHeadInvalid = false;
        }
    }

    public get revenueHead(): RevenueHeadPayload {
        return this.form.controls['revenueHead'].value;
    }

    private setUp(): void {
        this.form = this.fb.group({
            revenueHead: [null, [Validators.required]],
            title: ['', [Validators.required, Validators.min(4)]],
            isEnabled: [true, [Validators.required]],
        });
    }

    public onServiceGroupSelected(payload: ServiceGroupPayload) {
        if (payload && payload.id) {
            this.isServiceGroupInvalid = false;
            this.revenueHeadSearchComponent?.getRevenueHeadsByServiceGroup(payload.id);
        }
    }

    public onHospitalSelected(hospitalPayload: HospitalPayload) {
        if (hospitalPayload && hospitalPayload.id) {
            this.isHospitalInvalid = false;
            this.serviceGroupSearchComponent?.getServiceGroupByHospital(hospitalPayload.id);
        }
    }

    public submit(): void {
        let isInvalid = validateFormControls(this.form);
        if (isInvalid) {
            this.isRevenueHeadInvalid = isFormControlInvalid('revenueHead', this.form);
            this.msg.error('Some fields are invalid');
            return;
        }
        this.isLoading = true;
        const revenueHead: RevenueHeadPayload = this.form.controls['revenueHead'].value;
       
        this.sub.add(
            this.service.createService(this.form.value, revenueHead.id).subscribe({
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
        this.serviceGroupSearchComponent?.onClear();
        this.revenueHeadSearchComponent?.onClear();
        this.setUp();
    }

}
