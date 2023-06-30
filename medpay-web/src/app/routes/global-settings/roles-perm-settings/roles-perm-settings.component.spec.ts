import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { GlobalSettingsRolesPermSettingsComponent } from './roles-perm-settings.component';

describe('GlobalSettingsRolesPermSettingsComponent', () => {
  let component: GlobalSettingsRolesPermSettingsComponent;
  let fixture: ComponentFixture<GlobalSettingsRolesPermSettingsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ GlobalSettingsRolesPermSettingsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GlobalSettingsRolesPermSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
