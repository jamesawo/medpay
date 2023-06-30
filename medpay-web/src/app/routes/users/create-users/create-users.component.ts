import { ChangeDetectorRef, Component, ViewChild } from '@angular/core';
import { _HttpClient, ModalHelper } from '@delon/theme';
import { UserPayload } from '../_data/user.payload';
import { CreateUserPageOneComponent } from './components/create-user-page-one/create-user-page-one.component';
import { CreateUserPageTwoComponent } from './components/create-user-page-two/create-user-page-two.component';
import { CreateUserPageThreeComponent } from './components/create-user-page-three/create-user-page-three.component';

@Component({
    selector: 'app-users-create-users',
    templateUrl: './create-users.component.html',
})
export class UsersCreateUsersComponent {
    public index = 0;
    public selectedValue = '';
    public radioValue = '';
    public isLoading = false;
    public hasPendingSave = true;

    @ViewChild('createUserPageOneComponent')
    public createUserPageOneComponent?: CreateUserPageOneComponent;

    @ViewChild('createUserPageTwoComponent')
    public createUserPageTwoComponent?: CreateUserPageTwoComponent;

    @ViewChild('createUserPageThreeComponent')
    public createUserPageThreeComponent?: CreateUserPageThreeComponent;

    public user: UserPayload = new UserPayload();

    constructor(private http: _HttpClient, private modal: ModalHelper, private cdr: ChangeDetectorRef) {
    }


    public onIndexChange(index: number) {
        this.index = index;
    }

    public pre(): void {
        if (this.index >= 0) {
            this.index -= 1;
        }
    }

    public next(): void {
        if (this.index < 2) {
            this.index += 1;
        }
    }

    public submit() {
        this.isLoading = true;
        switch (this.index) {
            case 0:
                this.createUserPageOneComponent?.onSubmit();
                break;
            case 1:
                this.createUserPageTwoComponent?.submit();
                return;
            case 2:
                this.createUserPageThreeComponent?.onSubmit();
                return;
        }
    }

    public onUserChange(user: UserPayload) {
        this.isLoading = false;
        if (user) {
            this.user = user;
        }
        this.cdr.detectChanges();
    }
    
}
