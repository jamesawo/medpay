import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionReceiptViewerComponent } from './transaction-receipt-viewer.component';

describe('TransactionReceiptViewerComponent', () => {
  let component: TransactionReceiptViewerComponent;
  let fixture: ComponentFixture<TransactionReceiptViewerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TransactionReceiptViewerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransactionReceiptViewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
