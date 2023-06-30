import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoleMultipleSearchDropdownComponent } from './role-multiple-search-dropdown.component';

describe('RoleMultipleSearchDropdownComponent', () => {
  let component: RoleMultipleSearchDropdownComponent;
  let fixture: ComponentFixture<RoleMultipleSearchDropdownComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RoleMultipleSearchDropdownComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RoleMultipleSearchDropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
