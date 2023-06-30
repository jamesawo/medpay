import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Subscription } from 'rxjs';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { displayError } from '@shared';
import { RolePayload } from '../../_data/authorization.payload';
import { UserPayload } from '../../../users/_data/user.payload';
import { UserSearchDropdownComponent } from '../../../users/components/user-search-dropdown/user-search-dropdown.component';
import { AuthorizationService } from '../../authorization.service';

@Component({
    selector: 'app-authorization-user',
    templateUrl: './user.component.html',
    styles: [`
        .block{
            display: block;
        }
    `]
})
export class AuthorizationUserComponent implements OnInit, OnDestroy {
    public userPayload?: UserPayload;
    public isLoading = false;
    public isExpanded = false;

    public allRoles: RolePayload[] = [];
    public selectedUserRoles?: RolePayload[] = [];
    @ViewChild('userSearchDropdownComponent')
    public userSearchDropdownComponent?: UserSearchDropdownComponent;

    private sub = new Subscription();

    constructor(
        private msg: NzMessageService,
        private spinner: NgxSpinnerService,
        private toast: ToastrService,
        private service: AuthorizationService,
    ) {

    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    ngOnInit(): void {
        this.loadRoles();
    }

    public loadRoles() {
        this.spinner.show().then();
        this.sub.add(
            this.service.getAllRoles().subscribe({
                next: (res) => {
                    this.spinner.hide().then();
                    this.allRoles = res;
                },
                error: (err) => {
                    this.spinner.hide().then();
                    displayError(err, { toastService: this.toast });
                },
            }),
        );
    }

    public onResetPayload = () => {
        this.userPayload = undefined;
        this.selectedUserRoles = [];
        this.userSearchDropdownComponent?.onClear();
        this.msg.success('Reset, Done!');
    };

    public onCancelAction = () => {
        this.msg.info('Action Cancelled');
    };

    public onSearch = () => {};

    public submit() {
      this.isLoading = true;

      this.sub.add(
          this.service.assignRolesToUser(this.userPayload?.id, this.userPayload?.roles).subscribe({
              next: (res) => {
                  this.isLoading = false;
                  if (res){
                      this.toast.success("Updated Successfully");
                      this.onResetPayload();
                  }
              },
              error: (err) => {
                  this.isLoading = false;
                  displayError(err, {toastService: this.toast});
              }
          })
      )
    }

    public roleSelected(roles?: RolePayload[]) {
        if (this.userPayload && this.userPayload.id){
            this.selectedUserRoles = roles;
            this.userPayload.roles = roles;
            return;
        }
        this.toast.error('Select user first');
        return;
    }

    public userSelected(user?: UserPayload) {
        if (user) {
            this.userPayload = user;
            this.selectedUserRoles = user.roles;
        }
    }

    public get canDisplayInfo(): boolean {
         return !!(this.userPayload?.id && this.selectedUserRoles?.length);
    }

    public getUserInfo() {
        const basic = this.userPayload?.basicDetails;
        return `${basic?.firstName}, ${basic?.lastName} ${basic?.otherName}`

    }

    public onRemoveRoleFromUser(role: RolePayload) {
        if (this.userPayload && this.userPayload.roles && this.userPayload.roles.length > 0) {
            let roles: RolePayload[] = this.userPayload.roles;
            let index: number = roles.findIndex(value => value.id === role.id);
            if (index > -1){
                roles.splice(index, 1);
                this.userPayload.roles = [...roles];
                this.selectedUserRoles = [...roles];
            }
        }
    }
}
