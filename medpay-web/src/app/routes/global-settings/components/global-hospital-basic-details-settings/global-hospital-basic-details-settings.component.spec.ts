import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GlobalHospitalBasicDetailsSettingsComponent } from './global-hospital-basic-details-settings.component';

describe('GlobalHospitalBasicDetailsSettingsComponent', () => {
  let component: GlobalHospitalBasicDetailsSettingsComponent;
  let fixture: ComponentFixture<GlobalHospitalBasicDetailsSettingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GlobalHospitalBasicDetailsSettingsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GlobalHospitalBasicDetailsSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
