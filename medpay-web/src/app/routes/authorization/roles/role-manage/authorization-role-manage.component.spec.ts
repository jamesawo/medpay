import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthorizationRoleManageComponent } from './authorization-role-manage.component';

describe('RoleManageComponent', () => {
  let component: AuthorizationRoleManageComponent;
  let fixture: ComponentFixture<AuthorizationRoleManageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AuthorizationRoleManageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuthorizationRoleManageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
