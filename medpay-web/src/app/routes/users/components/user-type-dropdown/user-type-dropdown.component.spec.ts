import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserTypeDropdownComponent } from './user-type-dropdown.component';

describe('UserTypeDropdownComponent', () => {
  let component: UserTypeDropdownComponent;
  let fixture: ComponentFixture<UserTypeDropdownComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserTypeDropdownComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserTypeDropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
