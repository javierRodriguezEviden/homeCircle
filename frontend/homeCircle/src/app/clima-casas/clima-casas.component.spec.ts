import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClimaCasasComponent } from './clima-casas.component';

describe('ClimaCasasComponent', () => {
  let component: ClimaCasasComponent;
  let fixture: ComponentFixture<ClimaCasasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ClimaCasasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClimaCasasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
