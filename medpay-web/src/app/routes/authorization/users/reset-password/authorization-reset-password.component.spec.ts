import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthorizationResetPasswordComponent } from './authorization-reset-password.component';

describe('ResetPasswordComponent', () => {
  let component: AuthorizationResetPasswordComponent;
  let fixture: ComponentFixture<AuthorizationResetPasswordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AuthorizationResetPasswordComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuthorizationResetPasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
