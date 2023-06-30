import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { UsersUsersReportComponent } from './users-report.component';

describe('UsersUsersReportComponent', () => {
  let component: UsersUsersReportComponent;
  let fixture: ComponentFixture<UsersUsersReportComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ UsersUsersReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UsersUsersReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
