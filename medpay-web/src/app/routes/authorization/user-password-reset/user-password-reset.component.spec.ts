import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { AuthorizationUserPasswordResetComponent } from './user-password-reset.component';

describe('AuthorizationUserPasswordResetComponent', () => {
  let component: AuthorizationUserPasswordResetComponent;
  let fixture: ComponentFixture<AuthorizationUserPasswordResetComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ AuthorizationUserPasswordResetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuthorizationUserPasswordResetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
