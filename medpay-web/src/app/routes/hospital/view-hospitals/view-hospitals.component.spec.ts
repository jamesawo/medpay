import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { HospitalViewHospitalsComponent } from './view-hospitals.component';

describe('HospitalViewHospitalsComponent', () => {
  let component: HospitalViewHospitalsComponent;
  let fixture: ComponentFixture<HospitalViewHospitalsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ HospitalViewHospitalsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HospitalViewHospitalsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
