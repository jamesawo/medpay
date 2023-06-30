import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { AuthorizationRolesComponent } from './roles.component';

describe('AuthorizationRolesComponent', () => {
  let component: AuthorizationRolesComponent;
  let fixture: ComponentFixture<AuthorizationRolesComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ AuthorizationRolesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuthorizationRolesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
