import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { HospitalCreateHospitalComponent } from './create-hospital.component';

describe('HospitalCreateHospitalComponent', () => {
  let component: HospitalCreateHospitalComponent;
  let fixture: ComponentFixture<HospitalCreateHospitalComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ HospitalCreateHospitalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HospitalCreateHospitalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
