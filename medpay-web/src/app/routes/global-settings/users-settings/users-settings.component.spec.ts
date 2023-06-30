import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { GlobalSettingsUsersSettingsComponent } from './users-settings.component';

describe('GlobalSettingsUsersSettingsComponent', () => {
  let component: GlobalSettingsUsersSettingsComponent;
  let fixture: ComponentFixture<GlobalSettingsUsersSettingsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ GlobalSettingsUsersSettingsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GlobalSettingsUsersSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
