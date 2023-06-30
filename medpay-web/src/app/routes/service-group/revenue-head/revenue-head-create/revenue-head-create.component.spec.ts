import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RevenueHeadCreateComponent } from './revenue-head-create.component';

describe('RevenueHeadCreateComponent', () => {
  let component: RevenueHeadCreateComponent;
  let fixture: ComponentFixture<RevenueHeadCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RevenueHeadCreateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RevenueHeadCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
