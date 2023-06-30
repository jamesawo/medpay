import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { UsersCreateUsersComponent } from './create-users.component';

describe('UsersCreateUsersComponent', () => {
  let component: UsersCreateUsersComponent;
  let fixture: ComponentFixture<UsersCreateUsersComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ UsersCreateUsersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UsersCreateUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
