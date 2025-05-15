import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AlquilarComponent } from './alquilar.component';

describe('AlquilarComponent', () => {
  let component: AlquilarComponent;
  let fixture: ComponentFixture<AlquilarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AlquilarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AlquilarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
