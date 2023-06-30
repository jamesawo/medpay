import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { ServiceGroupPayload } from '../../_data/service-group.payload';
import { displayError } from '@shared';
import { NzMessageService } from 'ng-zorro-antd/message';
import { ToastrService } from 'ngx-toastr';
import { ServiceGroupService } from '../../service-group.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-service-group-search',
  templateUrl: './service-group-search.component.html',
  styles: [
  ]
})
export class ServiceGroupSearchComponent implements OnInit, OnDestroy {
    @Input()
    public props?: { hasError?: boolean, required?: boolean};

    @Input()
    public data?: ServiceGroupPayload[] = [];

    @Input()
    public value?: ServiceGroupPayload;

    @Output()
    public valueChange = new EventEmitter<ServiceGroupPayload>();

    private sub = new Subscription();
    constructor(
        private msg: NzMessageService,
        private toaster: ToastrService,
        private service: ServiceGroupService,
        private spinner: NgxSpinnerService,
    ) {
    }

    ngOnInit(): void {
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    public onSelected(selected: ServiceGroupPayload): void {
        this.valueChange.emit(selected);
    }

    public onClear() {
        this.value = undefined;
    }

    public getServiceGroupByHospital(hospitalId: number) {
        this.spinner.show().then();
        this.sub.add(
            this.service.getServiceGroupByHospital(hospitalId).subscribe({
                next: (res) => {
                    this.spinner.hide().then();
                    this.data = res;
                },
                error: (err) => {
                    this.spinner.hide().then();
                    displayError(err, { toastService: this.toaster });
                },
            }),
        );
    }
    
}
