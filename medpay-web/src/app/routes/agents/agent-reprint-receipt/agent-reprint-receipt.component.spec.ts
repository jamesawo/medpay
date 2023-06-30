import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgentReprintReceiptComponent } from './agent-reprint-receipt.component';

describe('AgentReprintReceiptComponent', () => {
  let component: AgentReprintReceiptComponent;
  let fixture: ComponentFixture<AgentReprintReceiptComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AgentReprintReceiptComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgentReprintReceiptComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
