import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { UsersViewUsersComponent } from './view-users.component';

describe('UsersViewUsersComponent', () => {
  let component: UsersViewUsersComponent;
  let fixture: ComponentFixture<UsersViewUsersComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ UsersViewUsersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UsersViewUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
