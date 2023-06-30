import { Component, Input, OnInit } from '@angular/core';
import { ServicePayload } from '../../../service-group/_data/service-group.payload';
import { PassportService } from '../../../passport/service/passport.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { ToastrService } from 'ngx-toastr';

@Component({
    selector: 'app-agents-service-payment',
    templateUrl: './agents-service-payment.component.html',
    styles: [],
})
export class AgentsServicePaymentComponent implements OnInit {
    public selectedServices: ServicePayload[] = [];
    public isLoading = false;

    @Input()
    public props?: {hospitalId?: number};
    constructor(
        private passportService: PassportService,
        private msg: NzMessageService,
        private toaster: ToastrService,
    ) {
    }

    ngOnInit(): void {
    }

    public onAdd = () => {
        if (!this.selectedServices.length) {
            this.toaster.warning('Add services first');
            return;
        }
    };

    private onResetTableProps(): void {

    }

    public onServiceSelected(selected: ServicePayload) {
        this.addToSelectedServices(selected);
    }

    public onProceed() {

    }

    private addToSelectedServices(service: ServicePayload) {
        // filter duplicate
        let index = this.selectedServices.findIndex(value => value.id === service.id);
        if (index > -1) {
        } else {
            this.selectedServices.push(service);
            this.selectedServices = [...this.selectedServices];
        }
    }

}
