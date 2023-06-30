import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HospitalSupportChannelDropdownComponent } from './hospital-support-channel-dropdown.component';

describe('HospitalSupportChannelDropdownComponent', () => {
  let component: HospitalSupportChannelDropdownComponent;
  let fixture: ComponentFixture<HospitalSupportChannelDropdownComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HospitalSupportChannelDropdownComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HospitalSupportChannelDropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
