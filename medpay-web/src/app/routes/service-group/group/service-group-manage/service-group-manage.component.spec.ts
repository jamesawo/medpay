import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceGroupManageComponent } from './service-group-manage.component';

describe('ServiceGroupManageComponent', () => {
  let component: ServiceGroupManageComponent;
  let fixture: ComponentFixture<ServiceGroupManageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServiceGroupManageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServiceGroupManageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
