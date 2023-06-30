import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GlobalHospitalApiSettingsComponent } from './global-hospital-api-settings.component';

describe('GlobalHospitalApiSettingsComponent', () => {
  let component: GlobalHospitalApiSettingsComponent;
  let fixture: ComponentFixture<GlobalHospitalApiSettingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GlobalHospitalApiSettingsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GlobalHospitalApiSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
