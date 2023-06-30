import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RevenueHeadSearchComponent } from './revenue-head-search.component';

describe('RevenueHeadSearchComponent', () => {
  let component: RevenueHeadSearchComponent;
  let fixture: ComponentFixture<RevenueHeadSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RevenueHeadSearchComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RevenueHeadSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
