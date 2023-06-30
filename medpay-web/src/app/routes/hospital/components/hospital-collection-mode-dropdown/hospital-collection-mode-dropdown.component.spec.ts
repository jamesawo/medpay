import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HospitalCollectionModeDropdownComponent } from './hospital-collection-mode-dropdown.component';

describe('HospitalCollectionModeDropdownComponent', () => {
  let component: HospitalCollectionModeDropdownComponent;
  let fixture: ComponentFixture<HospitalCollectionModeDropdownComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HospitalCollectionModeDropdownComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HospitalCollectionModeDropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
