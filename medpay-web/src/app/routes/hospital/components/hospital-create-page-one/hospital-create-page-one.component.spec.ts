import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HospitalCreatePageOneComponent } from './hospital-create-page-one.component';

describe('HospitalCreatePageOneComponent', () => {
  let component: HospitalCreatePageOneComponent;
  let fixture: ComponentFixture<HospitalCreatePageOneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HospitalCreatePageOneComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HospitalCreatePageOneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
