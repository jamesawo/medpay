import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgentMakePaymentComponent } from './agent-make-payment.component';

describe('MakePaymentComponent', () => {
  let component: AgentMakePaymentComponent;
  let fixture: ComponentFixture<AgentMakePaymentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AgentMakePaymentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgentMakePaymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
