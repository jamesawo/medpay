import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { TransactionsReconcilePaymentComponent } from './reconcile-payment.component';

describe('TransactionsReconcilePaymentComponent', () => {
  let component: TransactionsReconcilePaymentComponent;
  let fixture: ComponentFixture<TransactionsReconcilePaymentComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ TransactionsReconcilePaymentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransactionsReconcilePaymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
