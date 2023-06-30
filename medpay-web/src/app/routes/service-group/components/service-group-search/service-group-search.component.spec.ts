import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceGroupSearchComponent } from './service-group-search.component';

describe('ServiceGroupSearchComponent', () => {
  let component: ServiceGroupSearchComponent;
  let fixture: ComponentFixture<ServiceGroupSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServiceGroupSearchComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServiceGroupSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
