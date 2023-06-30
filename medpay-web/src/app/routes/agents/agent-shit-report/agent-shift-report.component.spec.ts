import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgentShiftReportComponent } from './agent-shift-report.component';

describe('DownloadReportComponent', () => {
  let component: AgentShiftReportComponent;
  let fixture: ComponentFixture<AgentShiftReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AgentShiftReportComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgentShiftReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
