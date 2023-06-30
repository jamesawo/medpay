import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HospitalCreatePageTwoComponent } from './hospital-create-page-two.component';

describe('HospitalCreatePageTwoComponent', () => {
  let component: HospitalCreatePageTwoComponent;
  let fixture: ComponentFixture<HospitalCreatePageTwoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HospitalCreatePageTwoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HospitalCreatePageTwoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
