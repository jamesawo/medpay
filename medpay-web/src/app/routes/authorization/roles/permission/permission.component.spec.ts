import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { AuthorizationPermissionComponent } from './permission.component';

describe('AuthorizationPermissionComponent', () => {
  let component: AuthorizationPermissionComponent;
  let fixture: ComponentFixture<AuthorizationPermissionComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ AuthorizationPermissionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuthorizationPermissionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
