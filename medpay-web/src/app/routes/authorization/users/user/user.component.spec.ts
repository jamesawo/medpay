import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { AuthorizationUserComponent } from './user.component';

describe('AuthorizationUserComponent', () => {
  let component: AuthorizationUserComponent;
  let fixture: ComponentFixture<AuthorizationUserComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ AuthorizationUserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuthorizationUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
