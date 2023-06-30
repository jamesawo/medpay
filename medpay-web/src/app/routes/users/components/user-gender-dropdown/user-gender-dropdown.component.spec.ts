import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserGenderDropdownComponent } from './user-gender-dropdown.component';

describe('UserGenderDropdownComponent', () => {
  let component: UserGenderDropdownComponent;
  let fixture: ComponentFixture<UserGenderDropdownComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserGenderDropdownComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserGenderDropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
