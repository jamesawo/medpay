import { Component, OnInit } from '@angular/core';
import { _HttpClient, ModalHelper } from '@delon/theme';

@Component({
    selector: 'app-users-users-report',
    templateUrl: './users-report.component.html',
})
export class UsersUsersReportComponent implements OnInit {
    constructor(private http: _HttpClient, private modal: ModalHelper) {}

    ngOnInit(): void {}

    add(): void {
        // this.modal
        //   .createStatic(FormEditComponent, { i: { id: 0 } })
        //   .subscribe(() => this.st.reload());
    }
}
