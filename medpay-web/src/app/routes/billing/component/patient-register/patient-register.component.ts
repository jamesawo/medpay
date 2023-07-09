import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {format} from 'date-fns';
import {BillingService} from "../../billing.service";
import {NgxSpinnerService} from "ngx-spinner";
import {PatientPayload} from "../../_data/patient.payload";
import {copy} from "@delon/util";
import {Clipboard} from "@angular/cdk/clipboard";
import {NzMessageService} from "ng-zorro-antd/message";

@Component({
    selector: 'app-patient-register',
    templateUrl: './patient-register.component.html',
    styles: []
})
export class PatientRegisterComponent implements OnInit {

    public form: FormGroup = this.buildForm;
    public showAlertNotification = false;
    public registeredNumber?: string;

    constructor(
        private fb: FormBuilder,
        private service: BillingService,
        private spinner: NgxSpinnerService,
        private clipboard: Clipboard,
        private msg: NzMessageService
    ) {
    }

    ngOnInit(): void {
    }

    public status(controlName: string): string {
        return this.form.dirty && this.form.touched && this.form.controls[controlName].invalid ? 'error' : 'success';
    }

    public get buildForm() {
        return this.fb.group({
            firstName: [null, [Validators.required]],
            lastName: [null, [Validators.required]],
            otherName: [null, [Validators.required]],
            dateOfBirth: [null, [Validators.required]],
            phone: [],
            address: [],
            gender: [null, [Validators.required]],
        })
    }

    public handleSubmit = () => {
        if (this.form.invalid) {
            this.markFormControls();
            return;
        }

        const formValue = this.form.value;
        formValue.dateOfBirth = this.format(formValue.dateOfBirth);
        this.onSubmitFormData(formValue);

    }

    private onSubmitFormData(payload: PatientPayload) {
        this.spinner.show().then();
        this.service.registerPatient(payload).subscribe({
            next: (response) => {
                this.spinner.hide().then();
                if (response.ok){
                    const patient = response.body;
                    this.registeredNumber = `${patient?.uniqueCode}`;
                    this.showAlertNotification = true;
                    this.resetForm();
                }
            },

            error: (error) => {
                this.spinner.hide().then();
            },
        })
    }

    public format(date: Date) {
        return format(date, 'yyyy-MM-dd');
    }

    private resetForm(){
        this.form.reset();
        this.form = this.buildForm;
    }


    private markFormControls() {
        this.form.markAllAsTouched();
        this.form.markAsDirty()
        Object.values(this.form.controls).forEach((control, index) => {
            control.markAsDirty();
            control.updateValueAndValidity();
        })
    }


    protected readonly copy = copy;

    public copyPatientNumber(value?: string) {
        if (value){
            this.clipboard.copy(value);
            this.msg.success(`${value} copied!`);
        }
    }
}
