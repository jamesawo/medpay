import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceGroupCreateComponent } from './service-group-create.component';

describe('ServiceGroupCreateComponent', () => {
  let component: ServiceGroupCreateComponent;
  let fixture: ComponentFixture<ServiceGroupCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServiceGroupCreateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServiceGroupCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
