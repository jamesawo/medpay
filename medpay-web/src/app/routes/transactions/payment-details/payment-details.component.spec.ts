import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { TransactionsPaymentDetailsComponent } from './payment-details.component';

describe('TransactionsPaymentDetailsComponent', () => {
  let component: TransactionsPaymentDetailsComponent;
  let fixture: ComponentFixture<TransactionsPaymentDetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ TransactionsPaymentDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransactionsPaymentDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
