import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateUserPageThreeComponent } from './create-user-page-three.component';

describe('CreateUserPageThreeComponent', () => {
  let component: CreateUserPageThreeComponent;
  let fixture: ComponentFixture<CreateUserPageThreeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateUserPageThreeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateUserPageThreeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
