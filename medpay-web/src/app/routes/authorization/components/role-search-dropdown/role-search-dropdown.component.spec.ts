import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoleSearchDropdownComponent } from './role-search-dropdown.component';

describe('RoleSearchDropdownComponent', () => {
  let component: RoleSearchDropdownComponent;
  let fixture: ComponentFixture<RoleSearchDropdownComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RoleSearchDropdownComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RoleSearchDropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
