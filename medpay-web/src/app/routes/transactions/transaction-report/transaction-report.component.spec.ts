import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { TransactionsTransactionReportComponent } from './transaction-report.component';

describe('TransactionsTransactionReportComponent', () => {
  let component: TransactionsTransactionReportComponent;
  let fixture: ComponentFixture<TransactionsTransactionReportComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ TransactionsTransactionReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransactionsTransactionReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
