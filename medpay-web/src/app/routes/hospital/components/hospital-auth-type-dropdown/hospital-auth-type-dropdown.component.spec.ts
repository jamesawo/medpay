import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HospitalAuthTypeDropdownComponent } from './hospital-auth-type-dropdown.component';

describe('HospitalAuthTypeDropdownComponent', () => {
  let component: HospitalAuthTypeDropdownComponent;
  let fixture: ComponentFixture<HospitalAuthTypeDropdownComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HospitalAuthTypeDropdownComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HospitalAuthTypeDropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
