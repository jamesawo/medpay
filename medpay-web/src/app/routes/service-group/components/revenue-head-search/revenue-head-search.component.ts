import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { RevenueHeadPayload } from '../../_data/service-group.payload';
import { displayError } from '@shared';
import { NzMessageService } from 'ng-zorro-antd/message';
import { ToastrService } from 'ngx-toastr';
import { ServiceGroupService } from '../../service-group.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { Subscription } from 'rxjs';

@Component({
    selector: 'app-revenue-head-search',
    templateUrl: './revenue-head-search.component.html',
    styles: [],
})
export class RevenueHeadSearchComponent implements OnInit, OnDestroy {

    @Input()
    public props?: { hasError?: boolean, required?: boolean, addOptionAll?: boolean };
    public optionAll: RevenueHeadPayload = new RevenueHeadPayload('ALL');


    @Input()
    public data?: RevenueHeadPayload[] = [];

    @Input()
    public value?: RevenueHeadPayload;

    @Output()
    public valueChange = new EventEmitter<RevenueHeadPayload>();

    private sub = new Subscription();

    public get hasData(): boolean {
        return this.data != null && this.data.length > 0;
    }

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

    public onSelected(selected: RevenueHeadPayload): void {
        this.valueChange.emit(selected);
    }

    public onClear() {
        this.value = undefined;
    }

    public getRevenueHeadsByServiceGroup(serviceGroupId: number) {
        this.spinner.show().then();
        this.sub.add(
            this.service.searchRevenueHead(serviceGroupId).subscribe({
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
