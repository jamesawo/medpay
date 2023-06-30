import { Component, OnInit } from '@angular/core';
import { PassportService } from '../../passport/service/passport.service';
import { HospitalPayload } from '../../hospital/data/hospital.payload';
import { ServicePayload } from '../../service-group/_data/service-group.payload';
import { NzMessageService } from 'ng-zorro-antd/message';
import { ToastrService } from 'ngx-toastr';
import { UserTypeEnum } from '../../users/_data/user.enum';


@Component({
    selector: 'app-agent-make-payment',
    templateUrl: './agent-make-payment.component.html',
    styles: [
        `
        `,
    ],
})
export class AgentMakePaymentComponent implements OnInit {

    public hospital?: HospitalPayload;
    public userId?: number;
    public billNumber = '';
    public isAgentProfile?: boolean;

    public service?: ServicePayload;

    constructor(
        private passportService: PassportService,
        private msg: NzMessageService,
        private toaster: ToastrService,
    ) {
    }

    ngOnInit(): void {
        this.onPrepUserDetails();
    }

    private onPrepUserDetails() {
        let response = this.passportService.getLoginResponse();
        if (response && response.hospital) {
            this.hospital = response.hospital;
            this.userId = response.id;
            this.isAgentProfile = response.type === UserTypeEnum.AGENT_USER;
        }
    }

    public onResetSearchPayload = () => {
        this.msg.success('Reset, Done!');
    };

    public onCancelAction = () => {
        this.msg.info('Action Cancelled');
    };


}
