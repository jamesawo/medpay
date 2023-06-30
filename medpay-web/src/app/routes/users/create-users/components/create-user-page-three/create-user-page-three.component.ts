import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserLimitTypeEnum } from '../../../_data/user.enum';
import { UserPayload } from '../../../_data/user.payload';
import { displayError, validateFormControls } from '@shared';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Subscription } from 'rxjs';
import { UsersService } from '../../../users.service';
import { ToastrService } from 'ngx-toastr';

@Component({
    selector: 'app-create-user-page-three',
    templateUrl: './create-user-page-three.component.html',
    styles: [],
})
export class CreateUserPageThreeComponent implements OnInit, OnDestroy {
    public form: FormGroup = new FormGroup({});
    public limitTypeHasError = false;

    @Input()
    public user?: UserPayload;

    @Output()
    public userChange = new EventEmitter<UserPayload>();

    private sub: Subscription = new Subscription();

    constructor(
        private fb: FormBuilder,
        private nzMessage: NzMessageService,
        private userService: UsersService,
        private toaster: ToastrService,
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
            currentAddress: [this.user?.address?.currentPlaceOfResidence, [Validators.required]],
            emergencyAddress: [this.user?.emergencyAddress?.currentPlaceOfResidence, [Validators.required]],
            limitType: [this.user?.configuration?.limitType, [Validators.required]],
            maxLimit: [this.user?.configuration?.maxLimit, [Validators.required]],
            minLimit: [this.user?.configuration?.minLimit, [Validators.required]],
        });
    }

    public onLimitTypeChange(value: UserLimitTypeEnum) {
        this.form.controls['limitType'].setValue(value);
        if (value) {
            this.limitTypeHasError = false;
        }
    }

    public onSubmit() {
        let isInvalid = validateFormControls(this.form);
        if (isInvalid) {
            this.onHandleInvalidForm();
            return;
        }

        if (!this.user || !this.user?.id) {
            this.toaster.error('Create user in previous steps first', 'User record not saved yet.');
            return;
        }
        const toUpdate: UserPayload = this.setUserPartialProperties(this.user.id);
        this.sub.add(
            this.userService.updateAddressAndLimitType(toUpdate).subscribe({
                next: (res) => this.onHandleRequestSaved(res),
                error: (err) => this.onHandleRequestError(err),
            }),
        );
    }

    private setUserPartialProperties(userId: number): UserPayload {
        const toUpdate: UserPayload = new UserPayload();
        toUpdate.id = userId;
        this.setConfigurationProperty(toUpdate);
        this.setAddressProperty(toUpdate);
        return toUpdate;
    }

    private setConfigurationProperty(toUpdate: UserPayload): void {
        const {limitType, maxLimit, minLimit} = this.form.value;
        toUpdate.configuration.maxLimit = maxLimit;
        toUpdate.configuration.minLimit = minLimit;
        toUpdate.configuration.limitType = limitType;
    }

    private setAddressProperty(toUpdate: UserPayload): void {
        const { currentAddress, emergencyAddress} = this.form.value;
        toUpdate.address.currentPlaceOfResidence = currentAddress;
        toUpdate.emergencyAddress.currentPlaceOfResidence = emergencyAddress;
    }

    private onHandleRequestSaved(res: UserPayload): void {
        if (res) {
            this.toaster.success('Created Successfully');
            this.userChange.emit(res);
        }else {
            this.toaster.error('Update Failed, Please check your entries and try again');
            this.userChange.emit(undefined);
        }
    }

    private onHandleRequestError(err: any): void {
        displayError(err, { toastService: this.toaster });
        this.userChange.emit(undefined);
    }

    private onHandleInvalidForm() {
        this.limitTypeHasError = this.form.controls['limitType'].invalid;
        this.userChange.emit(undefined);
    }
}
