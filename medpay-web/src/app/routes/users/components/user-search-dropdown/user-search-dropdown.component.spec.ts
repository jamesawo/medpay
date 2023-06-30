import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserSearchDropdownComponent } from './user-search-dropdown.component';

describe('UserSearchDropdownComponent', () => {
  let component: UserSearchDropdownComponent;
  let fixture: ComponentFixture<UserSearchDropdownComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserSearchDropdownComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserSearchDropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
