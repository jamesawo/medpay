import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { GlobalSettingsTransactionSettingsComponent } from './transaction-settings.component';

describe('GlobalSettingsTransactionSettingsComponent', () => {
  let component: GlobalSettingsTransactionSettingsComponent;
  let fixture: ComponentFixture<GlobalSettingsTransactionSettingsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ GlobalSettingsTransactionSettingsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GlobalSettingsTransactionSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
