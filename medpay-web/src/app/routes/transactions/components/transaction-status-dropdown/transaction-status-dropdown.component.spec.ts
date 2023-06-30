import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionStatusDropdownComponent } from './transaction-status-dropdown.component';

describe('TransactionStatusDropdownComponent', () => {
  let component: TransactionStatusDropdownComponent;
  let fixture: ComponentFixture<TransactionStatusDropdownComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TransactionStatusDropdownComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransactionStatusDropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
