import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgentsBillPaymentComponent } from './agents-bill-payment.component';

describe('AgentsBillPaymentComponent', () => {
  let component: AgentsBillPaymentComponent;
  let fixture: ComponentFixture<AgentsBillPaymentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AgentsBillPaymentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgentsBillPaymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
