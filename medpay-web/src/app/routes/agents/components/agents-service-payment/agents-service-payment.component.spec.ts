import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgentsServicePaymentComponent } from './agents-service-payment.component';

describe('AgentsServicePaymentComponent', () => {
  let component: AgentsServicePaymentComponent;
  let fixture: ComponentFixture<AgentsServicePaymentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AgentsServicePaymentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgentsServicePaymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
