import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserPreferredNameDropDownComponent } from './user-preferred-name-drop-down.component';

describe('UserPreferredNameComponent', () => {
  let component: UserPreferredNameDropDownComponent;
  let fixture: ComponentFixture<UserPreferredNameDropDownComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserPreferredNameDropDownComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserPreferredNameDropDownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
