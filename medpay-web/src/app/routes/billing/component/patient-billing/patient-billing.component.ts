import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {HospitalPayload} from "../../../hospital/data/hospital.payload";
import {PatientPayload} from "../../_data/patient.payload";
import {BillItemPayload, BillPayload, BillStatus} from "../../_data/bill.payload";
import {ServicePayload} from "../../../service-group/_data/service-group.payload";
import {NzMessageService} from "ng-zorro-antd/message";
import {NgxSpinnerService} from "ngx-spinner";
import {BillingService} from "../../billing.service";
import {PassportService} from "../../../passport/service/passport.service";
import {NzNotificationService} from "ng-zorro-antd/notification";
import {HospitalSearchComponent} from "../../../hospital/components/hospital-search/hospital-search.component";
import {PatientSearchComponent} from "../patient-search/patient-search.component";
import {ServiceSearchComponent} from "../../../service-group/components/service-search/service-search.component";

@Component({
    selector: 'app-patient-billing',
    templateUrl: './patient-billing.component.html',
    styles: [
        `
          .action-button {
            display: flex;
            flex-direction: row;
          }`
    ]
})
export class PatientBillingComponent implements OnInit {
    @ViewChild('template')
    public template?: TemplateRef<any>

    @ViewChild('hospitalSearchComponent')
    public hospitalComponent?: HospitalSearchComponent;

    @ViewChild('patientSearchComponent')
    public patientSearchComponent?: PatientSearchComponent;

    @ViewChild('serviceSearchComponent')
    public serviceSearchComponent?: ServiceSearchComponent;


    public bill = new BillPayload();
    public service?: ServicePayload;
    public amount?: number;
    public isTouched = false;


    public selectedHospitalId?: number;
    public invoiceNumber?: string;

    constructor(
        private msg: NzMessageService,
        private spinner: NgxSpinnerService,
        private billService: BillingService,
        private passport: PassportService,
        private nzNotification: NzNotificationService
    ) {
    }


    ngOnInit(): void {
    }

    public onSaveBill() {

        if (!this.bill.items.length){
            this.msg.warning('Add at least one bill item');
            return;
        }

        this.bill.createdBy = this.passport.getUser();
        this.bill.billAmount = this.getBillAmount();
        this.bill.status = BillStatus.UNPAID;

        this.spinner.show().then();

        this.billService.saveBill(this.bill).subscribe({
            next: (res) => {

                this.invoiceNumber = res.body?.billNumber;
                this.spinner.hide().then();
                this.onReset();
                this.showNotification();
            },
            error: () => {
                this.spinner.hide().then();
            }
        });

    }

    private showNotification(){
        if (this.template) {
            this.nzNotification.template(
                this.template!,
                { nzPlacement: 'top' }
            );
        }
    }

    private getBillAmount(): number {
        return this.bill.items.reduce((sum, item) =>
            sum + (Number(item.amount) || 0), 0);
    }

    public onAddItem() {

        if (!this.service && !this.amount) {
            return;
        }

        if (this.isItemExist()) {
            this.msg.warning('Similar Service Already Exist');
            return;
        }
        this.onAddBillItem();
    }

    public onAddBillItem() {
        const item: BillItemPayload = new BillItemPayload();
        item.amount = this.amount;
        item.service = this.service;

        this.bill.items = [...this.bill.items, item];
    }

    private isItemExist() {
        const items = this.bill.items;
        const found = items.find(value => value.service?.id === this.service?.id);
        return !!found;
    }

    public onHospitalSelected(hospitalPayload: HospitalPayload) {
        if (hospitalPayload && hospitalPayload.id) {
            this.selectedHospitalId = hospitalPayload.id;
            this.bill.hospital = hospitalPayload;
        }
    }

    public onPatientSelected(patient: PatientPayload) {

        if (patient) {
            this.bill.patient = patient;
        }
    }

    public onReset() {
        this.bill = new BillPayload();
        this.service = undefined;
        this.amount = undefined;

        this.serviceSearchComponent?.onClear()
        this.hospitalComponent?.onClear()
        this.patientSearchComponent?.onClear();
    }

    public onServiceChange(service: ServicePayload) {
        if (service) {
            this.service = service;
        }
    }

}
