import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { TransactionsReprintReceiptComponent } from './reprint-receipt.component';

describe('TransactionsReprintReceiptComponent', () => {
  let component: TransactionsReprintReceiptComponent;
  let fixture: ComponentFixture<TransactionsReprintReceiptComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ TransactionsReprintReceiptComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransactionsReprintReceiptComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
