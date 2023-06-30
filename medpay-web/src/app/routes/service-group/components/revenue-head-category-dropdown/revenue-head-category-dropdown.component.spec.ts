import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RevenueHeadCategoryDropdownComponent } from './revenue-head-category-dropdown.component';

describe('RevenueHeadCategoryDropdownComponent', () => {
  let component: RevenueHeadCategoryDropdownComponent;
  let fixture: ComponentFixture<RevenueHeadCategoryDropdownComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RevenueHeadCategoryDropdownComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RevenueHeadCategoryDropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
