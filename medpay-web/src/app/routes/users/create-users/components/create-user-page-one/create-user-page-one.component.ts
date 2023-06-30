import { ChangeDetectorRef, Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { UserPayload } from '../../../_data/user.payload';
import { ToastrService } from 'ngx-toastr';
import { NzMessageService } from 'ng-zorro-antd/message';
import { displayError, validateFormControls } from '@shared';
import { NgxSpinnerService } from 'ngx-spinner';
import { UserTypeEnum } from '../../../_data/user.enum';
import { HospitalPayload } from '../../../../hospital/data/hospital.payload';
import { UsersService } from '../../../users.service';

@Component({
    selector: 'app-create-user-page-one',
    templateUrl: './create-user-page-one.component.html',
    styles: [],
})
export class CreateUserPageOneComponent implements OnInit, OnDestroy {

    @Input()
    public setIsLoading?: (value: boolean) => void;

    @Input()
    public user?: UserPayload;

    @Output()
    public userChange = new EventEmitter<UserPayload>();

    public form: FormGroup = this.fb.group({});
    public isExpiryDateInvalid = false;
    public isUserTypeInvalid = false;

    private sub: Subscription = new Subscription();

    constructor(
        private fb: FormBuilder,
        private userService: UsersService,
        private toaster: ToastrService,
        private mgService: NzMessageService,
        private cdr: ChangeDetectorRef,
        private spinner: NgxSpinnerService,
    ) {
    }

    ngOnInit(): void {
        this.setForm();
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    public setForm() {
        this.form = this.fb.group({
            nickName: [this.user?.nickName, [Validators.required]],
            email: [this.user?.email, [Validators.required, Validators.email]],
            password: [this.user?.password, [Validators.required]],
            isEnabled: [this.user?.isEnabled, [Validators.required]],
            expiryDate: [this.user?.expiryDate, [Validators.required]],
            userTypeEnum: [this.user?.userTypeEnum, [Validators.required]],
        });
    }

    public async onSubmit(): Promise<void> {
        const isInvalid: boolean = validateFormControls(this.form);
        if (isInvalid) {
            this.isUserTypeInvalid = this.form.controls['userTypeEnum'].invalid;
            this.isExpiryDateInvalid = this.form.controls['expiryDate'].invalid;
            this.mgService.info('Some fields are invalid', {});
            this.userChange.emit(undefined);
            return;
        }
        let userPayload: UserPayload = Object.assign({ ...this.user }, this.form.value);
 
        this.sub.add(
            this.userService.createUser(userPayload).subscribe({
                next: (res) => {
                    if (this.user && res) {
                        this.toaster.success('Created Successfully');
                        res.password = userPayload.password;
                        res.hospital = userPayload.hospital;
                        this.userChange.emit(res);
                    }
                },
                error: (err) => {
                    displayError(err, { toastService: this.toaster });
                    this.userChange.emit(undefined);
                },
            }),
        );
    }

    public onExpiryDateSelected(value: string) {
        this.form.controls['expiryDate'].setValue(value);
        if (value) {
            this.isExpiryDateInvalid = false;
        }
    }

    public onUserTypeSelected(value: UserTypeEnum) {
        this.form.controls['userTypeEnum'].setValue(value);
        if (value) {
            this.isUserTypeInvalid = false;
        }
    }

    public onHospitalSelected(hospitalPayload: HospitalPayload) {
        if (this.user && hospitalPayload && hospitalPayload.id) {
            this.user.hospital = hospitalPayload;
        }
    }
}
