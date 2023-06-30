import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HospitalEnvironmentDropdownComponent } from './hospital-environment-dropdown.component';

describe('HospitalEnvironmentDropdownComponent', () => {
  let component: HospitalEnvironmentDropdownComponent;
  let fixture: ComponentFixture<HospitalEnvironmentDropdownComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HospitalEnvironmentDropdownComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HospitalEnvironmentDropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
