import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RevenueHeadManageComponent } from './revenue-head-manage.component';

describe('RevenueHeadManageComponent', () => {
  let component: RevenueHeadManageComponent;
  let fixture: ComponentFixture<RevenueHeadManageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RevenueHeadManageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RevenueHeadManageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
