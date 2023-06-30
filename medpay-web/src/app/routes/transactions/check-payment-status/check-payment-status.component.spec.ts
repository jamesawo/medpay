import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { TransactionsCheckPaymentStatusComponent } from './check-payment-status.component';

describe('TransactionsCheckPaymentStatusComponent', () => {
  let component: TransactionsCheckPaymentStatusComponent;
  let fixture: ComponentFixture<TransactionsCheckPaymentStatusComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ TransactionsCheckPaymentStatusComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransactionsCheckPaymentStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
