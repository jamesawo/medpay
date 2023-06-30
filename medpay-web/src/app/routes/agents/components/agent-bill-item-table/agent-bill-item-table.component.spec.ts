import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgentBillItemTableComponent } from './agent-bill-item-table.component';

describe('AgentBillItemTableComponent', () => {
  let component: AgentBillItemTableComponent;
  let fixture: ComponentFixture<AgentBillItemTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AgentBillItemTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgentBillItemTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
