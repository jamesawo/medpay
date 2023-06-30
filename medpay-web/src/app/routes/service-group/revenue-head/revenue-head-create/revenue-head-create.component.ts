import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { RevenueHeadPayload, ServiceGroupPayload } from '../../_data/service-group.payload';
import { ServiceGroupService } from '../../service-group.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd/message';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { HospitalPayload } from '../../../hospital/data/hospital.payload';
import { displayError, isFormControlInvalid, validateFormControls } from '@shared';
import { HospitalSearchComponent } from '../../../hospital/components/hospital-search/hospital-search.component';
import { NgxSpinnerService } from 'ngx-spinner';
import { ServiceCategoryEnum } from '../../_data/service-group.enum';
import { RevenueHeadCategoryDropdownComponent } from '../../components/revenue-head-category-dropdown/revenue-head-category-dropdown.component';
import { ServiceGroupSearchComponent } from '../../components/service-group-search/service-group-search.component';

@Component({
    selector: 'app-revenue-head-create',
    templateUrl: './revenue-head-create.component.html',
    styles: [],
})
export class RevenueHeadCreateComponent implements OnInit, OnDestroy {
    @ViewChild('hospitalSearchComponent')
    public hospitalSearchComponent?: HospitalSearchComponent;
    @ViewChild('serviceGroupSearchComponent')
    public serviceGroupSearchComponent?: ServiceGroupSearchComponent;
    @ViewChild('revenueHeadCategoryDropdownComponent')
    public revenueHeadCategoryDropdownComponent?: RevenueHeadCategoryDropdownComponent;
    public revenueHeadPayload: RevenueHeadPayload = new RevenueHeadPayload();
    public form: FormGroup = this.fb.group({});
    public isLoading = false;
    public serviceGroups: ServiceGroupPayload[] = [];
    public isServiceGroupInvalid = false;
    public isHospitalInvalid = false;
    public isCategoryInvalid =  false;

    private sub = new Subscription();


    constructor(
        private fb: FormBuilder,
        private msg: NzMessageService,
        private toaster: ToastrService,
        private service: ServiceGroupService,
        private spinner: NgxSpinnerService,
    ) {
    }

    ngOnInit(): void {
        this.setUp();
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    public set serviceGroup(payload: ServiceGroupPayload) {
        this.form.controls['serviceGroup'].setValue(payload);
        if (payload) this.isServiceGroupInvalid = false;
    }

    public get serviceGroup() {
        return this.form.controls['serviceGroup'].value;
    }

    public set isEnabled(value: boolean) {
        this.form.controls['isEnabled'].setValue(value);
    }

    public get isEnabled(): boolean {
        return this.form.controls['isEnabled'].value;
    }

    public set category(value: ServiceCategoryEnum) {
        this.form.controls['category'].setValue(value);
        if(value) this.isCategoryInvalid = false;
    }

    public get category(): ServiceCategoryEnum {
        return this.form.controls['category'].value;
    }

    private setUp(): void {
        this.form = this.fb.group({
            hospital: [null, [Validators.required]],
            serviceGroup: [null, [Validators.required]],
            title: ['', [Validators.required, Validators.min(4)]],
            isEnabled: [true, [Validators.required]],
            category: [null, [Validators.required]],

        });
    }

    public onHospitalSelected(hospitalPayload: HospitalPayload) {
        this.form.controls['hospital'].setValue(hospitalPayload);
        if (hospitalPayload && hospitalPayload.id) {
            this.isHospitalInvalid = false;
            this.serviceGroupSearchComponent?.getServiceGroupByHospital(hospitalPayload.id);
        }
    }

    public submit(): void {
        let isInvalid = validateFormControls(this.form);
        if (isInvalid) {
            this.checkIsFormControlsValid();
            this.msg.error('Some fields are invalid');
            return;
        }
        this.isLoading = true;
        const serviceGroupPayload = this.form.controls['serviceGroup'].value;
        this.sub.add(
            this.service.createRevenueHead(serviceGroupPayload.id, this.form.value).subscribe({
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
        this.revenueHeadCategoryDropdownComponent?.onClear();
        this.setUp();
    }

    private checkIsFormControlsValid(): void {
        this.isServiceGroupInvalid = isFormControlInvalid('serviceGroup', this.form);
        this.isHospitalInvalid = isFormControlInvalid('hospital', this.form);
        this.isCategoryInvalid = isFormControlInvalid('category', this.form);
    }
}
