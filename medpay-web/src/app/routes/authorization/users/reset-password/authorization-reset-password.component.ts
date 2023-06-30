import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { NzMessageService } from 'ng-zorro-antd/message';
import { ToastrService } from 'ngx-toastr';
import { NgxSpinnerService } from 'ngx-spinner';
import { compareValidator, displayError, isFormControlInvalid, validateFormControls } from '@shared';
import { UserPayload } from '../../../users/_data/user.payload';
import { UserSearchDropdownComponent } from '../../../users/components/user-search-dropdown/user-search-dropdown.component';
import { AuthorizationService } from '../../authorization.service';

@Component({
    selector: 'app-reset-password',
    templateUrl: './authorization-reset-password.component.html',
    styles: [],
})
export class AuthorizationResetPasswordComponent implements OnInit, OnDestroy {
    @ViewChild('userSearchDropdownComponent')
    public userSearchDropdownComponent?: UserSearchDropdownComponent;
    public form: FormGroup = this.fb.group({});
    // public resetPasswordPayload: ResetPasswordPayload = new ResetPasswordPayload();
    public isLoading = false;
    public isUserControlInvalid = false;

    private sub = new Subscription();

    constructor(
        private fb: FormBuilder,
        private msg: NzMessageService,
        private toaster: ToastrService,
        private service: AuthorizationService,
        private spinner: NgxSpinnerService,
    ) {
    }

    ngOnInit(): void {
        this.setUp();
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    private setUp(): void {
        this.form = this.fb.group({
            user: [null, [Validators.required]],
            password: ['', [Validators.required]],
            confirmPassword: ['', [Validators.required]],
        });
        this.form.addValidators(compareValidator(this.form.get('password')!, this.form.get('confirmPassword')!));
    }

    public submit(): void {
        let isInvalid = validateFormControls(this.form);
        if (isInvalid) {
            this.checkIsFormControlsValid();
            this.msg.error('Some fields are invalid');
            return;
        }
        this.isLoading = true;
        this.sub.add(
            this.service.resetPassword(this.form.value).subscribe({
                next: (res) => {
                    this.isLoading = false;
                    if (res) {
                        this.toaster.success('Successful');
                        this.resetPayload();
                    } else {
                        this.toaster.success('Failed');
                    }
                },
                error: (err) => {
                    this.isLoading = false;
                    displayError(err, {toastService: this.toaster})
                },
            })
        )
    }


    public resetPayload(): void {
        this.userSearchDropdownComponent?.onClear();
        this.form.reset();
        this.setUp();
    }

    private checkIsFormControlsValid(): void {
        this.isUserControlInvalid = isFormControlInvalid('user', this.form);
        if(this.form && this.form.errors && this.form.errors.hasOwnProperty('matchError') ){
            this.toaster.error("Password and Confirm must match");
        }
    }

    public userSelected(userPayload: UserPayload) {
        this.form.controls['user'].setValue(userPayload);
        if (userPayload && userPayload.id) {
            this.isUserControlInvalid = false;
        }
    }
}
