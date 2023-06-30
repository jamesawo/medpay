import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthorizationRoleCreateComponent } from './authorization-role-create.component';

describe('RoleCreateComponent', () => {
  let component: AuthorizationRoleCreateComponent;
  let fixture: ComponentFixture<AuthorizationRoleCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AuthorizationRoleCreateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuthorizationRoleCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
