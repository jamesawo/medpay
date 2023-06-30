import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HospitalCreatePageThreeComponent } from './hospital-create-page-three.component';

describe('HospitalCreatePageThreeComponent', () => {
  let component: HospitalCreatePageThreeComponent;
  let fixture: ComponentFixture<HospitalCreatePageThreeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HospitalCreatePageThreeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HospitalCreatePageThreeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
