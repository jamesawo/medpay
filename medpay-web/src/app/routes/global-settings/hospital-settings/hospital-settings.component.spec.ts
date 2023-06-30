import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { GlobalSettingsHospitalSettingsComponent } from './hospital-settings.component';

describe('GlobalSettingsHospitalSettingsComponent', () => {
  let component: GlobalSettingsHospitalSettingsComponent;
  let fixture: ComponentFixture<GlobalSettingsHospitalSettingsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ GlobalSettingsHospitalSettingsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GlobalSettingsHospitalSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
