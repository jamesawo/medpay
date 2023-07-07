import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { BehaviorSubject, Subject, Subscription } from 'rxjs';
import { HospitalPayload } from '../../data/hospital.payload';
import { environment } from '@env/environment';
import { _HttpClient } from '@delon/theme';
import { distinctUntilChanged, filter, tap } from 'rxjs/operators';

@Component({
    selector: 'app-hospital-search',
    templateUrl: './hospital-search.component.html',
    styles: [],
})
export class HospitalSearchComponent implements OnInit, OnDestroy {
    @Input('defaultSelected') // use this to pass in a hospital as default selected, when the component renders this value will be selected in the dropdown
    public selectedHospital?: HospitalPayload;
    public options: HospitalPayload[] = [];

    public isLoading = false;
    public searchInput$ = new Subject<string>();
    public minLengthTerm = 4;

    private url: string = environment.api.baseUrl + '/hospital/search-by-name';
    private loading: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
    private subscription: Subscription = new Subscription();

    @Output('selected') // use this to get the selected hospital when the user click on one of the result in the drop-down
    public selected: EventEmitter<HospitalPayload> = new EventEmitter<HospitalPayload>();

    @Output('value') // use this to get the search text as the user is typing
    public value: EventEmitter<string> = new EventEmitter<string>();

    @Input('props')
    public props?: {trim?: boolean, required?: boolean, autoFocus?: boolean, hasError?: boolean};

    constructor(private http: _HttpClient) {
    }

    ngOnInit(): void {
        if (this.selectedHospital) {
            if (this.selectedHospital.id) {
                this.options.push(this.selectedHospital);
            }else{
                this.selectedHospital = undefined;
            }
        }
        this.onInitialize();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    public onInput(event: Event): void  {
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

    private onPrepareSearch(value : string) {
        let extraUrlParams = ``;
        if (this.props){
            if (this.props.trim === true) {
                extraUrlParams += `&trim=true`
            }
            else if(this.props.trim === false){
                extraUrlParams += `&trim=false`
            }
        }
        const url = `${this.url}?search=${value}${extraUrlParams}`;
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

    public onSelected(value: HospitalPayload) {
        if (value) {
            this.selectedHospital = value;
            this.selected.emit(value);
        }
    }

    public onClear(){
        this.selectedHospital = undefined;
        this.options = [];

    }

}
