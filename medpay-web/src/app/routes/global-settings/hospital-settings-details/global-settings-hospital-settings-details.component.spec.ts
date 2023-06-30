import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GlobalSettingsHospitalSettingsDetailsComponent } from './global-settings-hospital-settings-details.component';

describe('HospitalSettingsDetailsComponent', () => {
  let component: GlobalSettingsHospitalSettingsDetailsComponent;
  let fixture: ComponentFixture<GlobalSettingsHospitalSettingsDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GlobalSettingsHospitalSettingsDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GlobalSettingsHospitalSettingsDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
