import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionRefAndSerialOrBillInputComponent } from './transaction-ref-and-serial-or-bill-input.component';

describe('TransactionSearchComponent', () => {
  let component: TransactionRefAndSerialOrBillInputComponent;
  let fixture: ComponentFixture<TransactionRefAndSerialOrBillInputComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TransactionRefAndSerialOrBillInputComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransactionRefAndSerialOrBillInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
