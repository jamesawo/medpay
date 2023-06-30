import {
    Component,
    EventEmitter,
    Input,
    OnChanges,
    OnDestroy,
    OnInit,
    Output,
    SimpleChanges
} from '@angular/core';
import {RolePayload} from "../../_data/authorization.payload";
import {_HttpClient} from "@delon/theme";
import {AuthorizationService} from "../../authorization.service";
import {
    distinctUntilChanged,
    filter,
    tap
} from "rxjs/operators";
import {
    BehaviorSubject,
    Subject,
    Subscription
} from "rxjs";

@Component({
  selector: 'app-role-multiple-search-dropdown',
  templateUrl: './role-multiple-search-dropdown.component.html',
  styles: [
      `
          nz-select {
              width: 100%;
          }
      `
  ]
})
export class RoleMultipleSearchDropdownComponent implements OnInit , OnDestroy, OnChanges {
    public listOfOption: RolePayload[] = [];
    public isLoading = false;

    @Input()
    public value?: RolePayload[] = []; // list of selected option

    @Output()
    public valueChange: EventEmitter<RolePayload[]> = new EventEmitter<RolePayload[]>();

    @Input()
    public props?: { required?: boolean, hasError?: boolean };

    private sub = new Subscription();

    constructor(private roleService: AuthorizationService) {
    }

    ngOnInit(): void {
        this.listOfOption = [];
        this.loadListOfRoleData();
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    ngOnChanges(changes: SimpleChanges): void {
        this.loadDefaultSelected();
    }

    private loadDefaultSelected(){
        let selected: RolePayload[] = [];
        if (this.value && this.value.length > 0) {
            this.value.forEach(roleInUserSelected => {
                let index = this.listOfOption.findIndex(roleInMainList => roleInMainList.id === roleInUserSelected.id);
                if (index > -1) {
                    selected.push(this.listOfOption[index]);
                }
            });
            this.value = [...selected];
            this.listOfOption = [...this.listOfOption];
        }
    }

    private loadListOfRoleData(): void {
        this.isLoading = true;
        this.sub.add(
            this.roleService.getAllRoles().subscribe(value => {
                this.listOfOption = value;
                this.loadDefaultSelected();
                this.isLoading = false;
            }),
        );
    }

    public onSelected(value: RolePayload[]) {
        this.value = value;
        this.valueChange.emit(value);
    }

    public onClear() {
        this.value = [];
    }


}
