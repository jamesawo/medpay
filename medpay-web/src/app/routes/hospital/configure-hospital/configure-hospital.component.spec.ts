import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { HospitalConfigureHospitalComponent } from './configure-hospital.component';

describe('HospitalConfigureHospitalComponent', () => {
  let component: HospitalConfigureHospitalComponent;
  let fixture: ComponentFixture<HospitalConfigureHospitalComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ HospitalConfigureHospitalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HospitalConfigureHospitalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
