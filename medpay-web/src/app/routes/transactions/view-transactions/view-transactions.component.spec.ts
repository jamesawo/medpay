import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { TransactionsViewTransactionsComponent } from './view-transactions.component';

describe('TransactionsViewTransactionsComponent', () => {
  let component: TransactionsViewTransactionsComponent;
  let fixture: ComponentFixture<TransactionsViewTransactionsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ TransactionsViewTransactionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransactionsViewTransactionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
