import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GlobalHospitalCollectionModeSettingsComponent } from './global-hospital-collection-mode-settings.component';

describe('GlobalHospitalCollectionModeSettingsComponent', () => {
  let component: GlobalHospitalCollectionModeSettingsComponent;
  let fixture: ComponentFixture<GlobalHospitalCollectionModeSettingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GlobalHospitalCollectionModeSettingsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GlobalHospitalCollectionModeSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
