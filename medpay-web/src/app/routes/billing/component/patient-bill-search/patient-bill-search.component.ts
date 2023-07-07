import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {BehaviorSubject, filter, Subject, Subscription} from "rxjs";
import {distinctUntilChanged, tap} from "rxjs/operators";
import {environment} from "@env/environment";
import {_HttpClient} from "@delon/theme";
import {BillPayload} from "../../_data/bill.payload";


@Component({
    selector: 'app-patient-bill-search',
    templateUrl: './patient-bill-search.component.html',
    styles: []
})
export class PatientBillSearchComponent implements OnInit, OnDestroy {

    @Input('defaultSelected')
    public selectedPayload?: BillPayload;
    public options: BillPayload[] = [];

    public isLoading = false;
    public searchInput$ = new Subject<string>();
    public minLengthTerm = 4;

    private url: string = environment.api.baseUrl + '/billing/search';
    private loading: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
    private subscription: Subscription = new Subscription();

    @Output('selected')
    public selected: EventEmitter<BillPayload> = new EventEmitter<BillPayload>();

    @Output('value') // use this to get the search text as the user is typing
    public value: EventEmitter<string> = new EventEmitter<string>();

    @Input('props')
    public props?: { trim?: boolean, required?: boolean, autoFocus?: boolean, hasError?: boolean };

    @Input()
    hospital: number | undefined;


    constructor(private http: _HttpClient) {
    }

    ngOnInit(): void {
        if (this.selectedPayload) {
            if (this.selectedPayload.id) {
                this.options.push(this.selectedPayload);
            } else {
                this.selectedPayload = undefined;
            }
        }
        this.onInitialize();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    public onInput(event: Event): void {
        const value = (event.target as HTMLInputElement).value;
        this.value.emit(value);
        this.searchInput$.next(value);
    }

    private onInitialize() {
        this.subscription.add(this.loading.asObservable().subscribe((value) => this.isLoading = value),);

        this.subscription.add(
            this.searchInput$
                .pipe(
                    filter((res) => res !== null && res.length >= this.minLengthTerm),
                    distinctUntilChanged(),
                    tap(() => this.loading.next(true)),
                )
                .subscribe(value => this.onPrepareSearch(value))
        );
    }

    private onPrepareSearch(value: string) {
        let extraUrlParams = ``;
        if (this.props) {
            if (this.props.trim === true) {
                extraUrlParams += `&trim=true`
            } else if (this.props.trim === false) {
                extraUrlParams += `&trim=false`
            }
        }
        const url = `${this.url}?term=${value}&hospital=${this.hospital}${extraUrlParams}`;
        this.onSearchData(url);
    }

    private onSearchData(url: string): void {
        this.subscription.add(
            this.http.get(url).subscribe(value => {
                this.options = value;
                this.loading.next(false);
            })
        )
    }

    public onSelected(value: BillPayload) {
        if (value) {
            this.selectedPayload = value;
            this.selected.emit(value);
        }
    }

    public onClear() {
        this.selectedPayload = undefined;
        this.options = [];

    }

    contactLabel(data: BillPayload) {
        return `Invoice Number: ${data.billNumber} - Amount: ${data.billAmount?.toFixed(2)}`;
    }
}
