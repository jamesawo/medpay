import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportDailyCollectionComponent } from './report-daily-collection.component';

describe('ReportDailyCollectionComponent', () => {
  let component: ReportDailyCollectionComponent;
  let fixture: ComponentFixture<ReportDailyCollectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReportDailyCollectionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReportDailyCollectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
