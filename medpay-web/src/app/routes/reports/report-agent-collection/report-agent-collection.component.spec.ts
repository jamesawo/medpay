import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportAgentCollectionComponent } from './report-agent-collection.component';

describe('ReportAgentCollectionComponent', () => {
  let component: ReportAgentCollectionComponent;
  let fixture: ComponentFixture<ReportAgentCollectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReportAgentCollectionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReportAgentCollectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
