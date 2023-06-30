import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { BehaviorSubject, Subject, Subscription } from 'rxjs';
import { _HttpClient } from '@delon/theme';
import { distinctUntilChanged, filter, tap } from 'rxjs/operators';
import { RolePayload } from '../../_data/authorization.payload';
import {AuthorizationService} from "../../authorization.service";

@Component({
    selector: 'app-role-search-dropdown',
    templateUrl: './role-search-dropdown.component.html',
    styles: [],
})
export class RoleSearchDropdownComponent implements OnInit, OnDestroy {
    public options: RolePayload[] = [];
    public isLoading = false;
    public searchInput$ = new Subject<string>();
    public minLengthTerm = 4;

    private loading: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
    private subscription: Subscription = new Subscription();

    @Input()
    public value?: RolePayload;

    @Output()
    public valueChange: EventEmitter<RolePayload> = new EventEmitter<RolePayload>();

    @Output()
    public valueInputChange: EventEmitter<string> = new EventEmitter<string>();

    @Input('props')
    public props?: { trim?: boolean, required?: boolean, autoFocus?: boolean, hasError?: boolean };

    constructor(private http: _HttpClient, private roleService: AuthorizationService) {
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
        this.subscription.add(this.searchInput$.pipe(filter((res) => res !== null && res.length >= this.minLengthTerm),
            distinctUntilChanged(),
            tap(() => this.loading.next(true)),)
                .subscribe(value => this.onSearchData(value)),
        );
    }

    private onSearchData(searchTerm: string): void {
        this.subscription.add(
            this.roleService.getRoleByTitle(searchTerm).subscribe(value => {
                this.options = value;
                this.loading.next(false);
            }),
        );
    }

    public onSelected(value: RolePayload) {
        if (value) {
            this.value = value;
            this.valueChange.emit(value);
        }
    }

    public onClear() {
        this.value = undefined;
        this.options = [];
    }

}
