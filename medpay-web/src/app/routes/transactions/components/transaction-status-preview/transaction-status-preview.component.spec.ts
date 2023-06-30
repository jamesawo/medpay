import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionStatusPreviewComponent } from './transaction-status-preview.component';

describe('TransactionStatusPreviewComponent', () => {
  let component: TransactionStatusPreviewComponent;
  let fixture: ComponentFixture<TransactionStatusPreviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TransactionStatusPreviewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransactionStatusPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
