import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeRegistradoComponent } from './home-registrado.component';

describe('HomeRegistradoComponent', () => {
  let component: HomeRegistradoComponent;
  let fixture: ComponentFixture<HomeRegistradoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HomeRegistradoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomeRegistradoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
