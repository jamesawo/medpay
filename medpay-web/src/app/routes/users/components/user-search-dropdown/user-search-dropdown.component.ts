import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { UserPayload } from '../../_data/user.payload';
import { _HttpClient } from '@delon/theme';
import { distinctUntilChanged, filter, tap } from 'rxjs/operators';
import { BehaviorSubject, Subject, Subscription } from 'rxjs';
import { UsersService } from '../../users.service';


@Component({
  selector: 'app-user-search-dropdown',
  templateUrl: './user-search-dropdown.component.html',
  styles: [
  ]
})
export class UserSearchDropdownComponent implements OnInit, OnDestroy {

    public options: UserPayload[] = [];
    public isLoading = false;
    public searchInput$ = new Subject<string>();
    public minLengthTerm = 4;

    private loading: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
    private subscription: Subscription = new Subscription();

    @Input()
    public value?: UserPayload;

    @Output()
    public valueChange: EventEmitter<UserPayload> = new EventEmitter<UserPayload>();

    @Output()
    public valueInputChange: EventEmitter<string> = new EventEmitter<string>();

    @Input()
    public props?: { trim?: boolean, required?: boolean, autoFocus?: boolean, hasError?: boolean };

    constructor(private http: _HttpClient,
                private userService: UsersService
    ) {
    }

    ngOnInit(): void {
        if (this.value && this.value.id) {
            this.options.push(this.value);
        }
        this.onInitialize();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    public onInput(event: Event): void {
        const value = (event.target as HTMLInputElement).value;
        this.valueInputChange.emit(value);
        this.searchInput$.next(value);
    }

    private onInitialize() {
        this.subscription.add(this.loading.asObservable().subscribe((value) => this.isLoading = value));
        this.subscription.add(
            this.searchInput$
                .pipe(
                    filter((res) => res !== null && res.length >= this.minLengthTerm),
                    distinctUntilChanged(),
                    tap(() => this.loading.next(true)),
                )
                .subscribe(value => this.onSearchData(value)),
        );
    }

    /*
    randomUserUrl = 'https://api.randomuser.me/?results=10';
    optionList: string[] = [];
    searchChange$ = new BehaviorSubject('');

    private onSearch() {
        const getRandomNameList = (name: string): Observable<any> =>
            this.http
                .get(`${this.randomUserUrl}`)
                .pipe(catchError(() => of({ results: [] })),
                    map((res: any) => res.results))
                .pipe(map((list: any) => list.map((item: any) => `${item.name.first} ${name}`)));

        const optionList$: Observable<string[]> = this.searchChange$
            .asObservable()
            .pipe(debounceTime(500))
            .pipe(switchMap(getRandomNameList));

        optionList$.subscribe(data => {
            console.log(data);
            this.isLoading = false;
        });
    }
    */

    private onSearchData(term: string): void {
        this.subscription.add(
            this.userService.searchByTerm(term).subscribe(value => {
                this.options = value;
                this.loading.next(false);
            })
        );
    }

    public onSelected(value: UserPayload) {
        if (value) {
            this.value = value;
            this.valueChange.emit(value);
        }
    }

    public onClear() {
        this.value = undefined;
        this.options = [];
    }

    public concatName(data: UserPayload) {
        const fullName = `${data.basicDetails.firstName} ${data.basicDetails.lastName} ${data.basicDetails.otherName}`;
        return `${fullName} - ${data.nickName}`
    }
}
