import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateUserPageOneComponent } from './create-user-page-one.component';

describe('CreateUserPageOneComponent', () => {
  let component: CreateUserPageOneComponent;
  let fixture: ComponentFixture<CreateUserPageOneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateUserPageOneComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateUserPageOneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
