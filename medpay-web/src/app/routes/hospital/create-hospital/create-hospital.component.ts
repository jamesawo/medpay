import { Component, OnDestroy, OnInit } from '@angular/core';
import { ModalHelper } from '@delon/theme';
import { HospitalPayload } from '../data/hospital.payload';
import { HospitalService } from '../hospital.service';
import { EMPTY_FIELDS } from '../../../shared/utils/messages';
import { finalize, Subscription } from 'rxjs';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';

@Component({
    selector: 'app-hospital-create-hospital',
    templateUrl: './create-hospital.component.html',
    styles: [``],
})
export class HospitalCreateHospitalComponent implements OnInit, OnDestroy {

    public hospitalPayload: HospitalPayload = new HospitalPayload();
    public error = '';
    public errorList: string[] = [];
    public index = 0;
    public steps: number[] = [];

    private subscription: Subscription = new Subscription();

    constructor(private service: HospitalService,
                private modal: ModalHelper,
                private spinner: NgxSpinnerService,
                private toaster: ToastrService,
                ) {
    }

    ngOnInit(): void {
        this.steps = Array(2);
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    public pre(): void {
        this.index -= 1;
    }

    public next(): void {
        this.index += 1;
    }

    public onIndexChange(index: number): void {
        this.index = index;
    }

    public submit() {
        let validate = this.onValidatePayload();
        if (!validate) {
            return;
        }
        this.onClearError();
        this.spinner.show().then();
        this.subscription.add(
            this.service.onCreateHospital(this.hospitalPayload)
                .pipe(
                    finalize(() => {
                        this.spinner.hide().then();
                    }),
                )
                .subscribe({
                    next: (res) => {
                        this.toaster.success('Created Successfully');

                    },
                    error: (err) => {
                        this.toaster.error('An error occurred');
                        this.displayError(err);
                    }
                })
        );
    }

    public addStep = (value: boolean): void => {
        if(this.steps.length >= 2 ){
            if (value) {
                this.steps.push(0);
                return;
            }
            else {
                this.steps.pop();
                return;
            }
        }
    };

    public onClearError(): void {
        this.error = '';
        this.errorList = [];
    }

    protected onValidatePayload(): boolean {
        let validate = this.hospitalPayload.validate();
        if (!validate.status){
            this.error = EMPTY_FIELDS;
            this.errorList = validate.messages;
        }
        return validate.status;
    }

    private displayError(e: any): void {
        const { message, error } = e.error;
        this.error = message;
        this.errorList = error;
    }
}

