import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserLimitTypeDropdownComponent } from './user-limit-type-dropdown.component';

describe('UserLimitTypeDropdownComponent', () => {
  let component: UserLimitTypeDropdownComponent;
  let fixture: ComponentFixture<UserLimitTypeDropdownComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserLimitTypeDropdownComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserLimitTypeDropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
