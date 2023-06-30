import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { BehaviorSubject, Subject, Subscription } from 'rxjs';
import { distinctUntilChanged, filter, tap } from 'rxjs/operators';
import { ServicePayload } from '../../_data/service-group.payload';
import { ServiceGroupService } from '../../service-group.service';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
    selector: 'app-service-search',
    templateUrl: './service-search.component.html',
    styles: [],
})
export class ServiceSearchComponent implements OnInit, OnDestroy {
    @Input()
    public value?: ServicePayload;
    public options: ServicePayload[] = [];

    public isLoading = false;
    public searchInput$ = new Subject<string>();
    public minLengthTerm = 4;


    private loading: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
    private subscription: Subscription = new Subscription();

    @Output()
    public valueChange: EventEmitter<ServicePayload> = new EventEmitter<ServicePayload>();


    @Input()
    public props?: { hospitalId?: number, trim?: boolean, required?: boolean, autoFocus?: boolean, hasError?: boolean };

    constructor(private service: ServiceGroupService, private msg: NzMessageService) {
    }

    ngOnInit(): void {
        this.onInitialize();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }


    public onInput(event: Event): void {
        const value = (event.target as HTMLInputElement).value;
        this.searchInput$.next(value);
    }

    private onInitialize() {
        this.subscription.add(this.loading.asObservable().subscribe((value) => this.isLoading = value));
        this.subscription.add(
            this.searchInput$.pipe(filter((res) => res !== null && res.length >= this.minLengthTerm),
                distinctUntilChanged(),
                tap(() => this.loading.next(true)))
                .subscribe(value => this.onSearchData(value)),
        );
    }


    private onSearchData(term: string): void {
        if (this.props && this.props.hospitalId) {
            this.subscription.add(
                this.service.searchServiceByTitleAndHospital(this.props.hospitalId, term).subscribe(value => {
                    this.options = value;
                    this.loading.next(false);
                }),
            );
        } else {
            this.options = [];
            this.loading.next(false);
            this.msg.warning('Hospital is not present in service search component');
            this.msg.warning('Assign a hospital to the current user');
        }
    }

    public onSelected(value: ServicePayload) {
        if (value) {
            this.value = value;
            this.valueChange.emit(value);
        }
    }

    public onClear() {
        this.searchInput$.next("");
        this.value = undefined;
        this.options = [];
    }
}
