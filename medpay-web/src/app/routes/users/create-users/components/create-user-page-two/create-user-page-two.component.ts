import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { UserBasicDetailsPayload, UserPayload } from '../../../_data/user.payload';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { UsersService } from '../../../users.service';
import { ToastrService } from 'ngx-toastr';
import { NzMessageService } from 'ng-zorro-antd/message';
import { displayError, validateFormControls } from '@shared';
import { UserGenderEnum, UserPreferredNameEnum } from '../../../_data/user.enum';

@Component({
    selector: 'app-create-user-page-two',
    templateUrl: './create-user-page-two.component.html',
    styles: [],
})
export class CreateUserPageTwoComponent implements OnInit, OnDestroy {
    @Input()
    public user?: UserPayload;

    @Output()
    public userChange = new EventEmitter<UserPayload>();

    public form: FormGroup = this.fb.group({});
    public isGenderInvalid = false;

    private sub: Subscription = new Subscription();

    constructor(
        private fb: FormBuilder,
        private userService: UsersService,
        private toaster: ToastrService,
        private mgService: NzMessageService,
    ) {
    }

    ngOnInit(): void {
        this.setForm();
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    public setForm() {
        let basicDetails = this.user?.basicDetails;
        this.form = this.fb.group({
            firstName: [basicDetails?.firstName, [Validators.required]],
            lastName: [basicDetails?.lastName, [Validators.required]],
            otherName: [basicDetails?.lastName, [Validators.required]],
            phoneNumber: [basicDetails?.phoneNumber, [Validators.required]],
            gender: [basicDetails?.gender, [Validators.required]],
            preferredPronoun: [basicDetails?.preferredPronoun, []],
            preferredName: [basicDetails?.preferredName, []],
            socialFb: [basicDetails?.socialFb, []],
            socialIg: [basicDetails?.socialIg, []],
            socialLkd: [basicDetails?.socialLkd, []],
            personalStatement: [basicDetails?.personalStatement, []],
            emergencyPhone: [basicDetails?.emergencyPhone, []],
        });
    }

    public submit() {
        const isInvalid: boolean = validateFormControls(this.form);
        if (isInvalid) {
            this.isGenderInvalid = this.form.controls['gender'].invalid;
            this.mgService.info('Some fields are invalid', {});
            this.userChange.emit(undefined);
            return;
        }
        const id = this.user?.id;
        const payload : UserBasicDetailsPayload = this.form.value;
        this.sub.add(
            this.userService.updateUserBasicDetails(id, payload).subscribe({
                next: (res) => {
                    if (this.user && res) {
                        this.toaster.success('Created Successfully');
                        this.user.basicDetails = res;
                        this.userChange.emit(this.user);
                    }
                },
                error: (err) => {
                    displayError(err, { toastService: this.toaster });
                    this.userChange.emit(undefined);
                },
            }),
        );
    }

    public isInvalid(formControl: string){
        return this.form.controls[formControl].dirty && this.form.controls[formControl].invalid;
    }

    public onGenderSelected(value: UserGenderEnum ){
        this.form.controls['gender'].setValue(value);
        if (value){
            this.isGenderInvalid = false;
        }
    }

    public onPreferredNameSelected(value: UserPreferredNameEnum) {
        this.form.controls['preferredName'].setValue(value);
    }
}
