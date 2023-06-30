import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateUserPageTwoComponent } from './create-user-page-two.component';

describe('CreateUserPageTwoComponent', () => {
  let component: CreateUserPageTwoComponent;
  let fixture: ComponentFixture<CreateUserPageTwoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateUserPageTwoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateUserPageTwoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
