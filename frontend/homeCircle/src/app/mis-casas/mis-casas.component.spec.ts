import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MisCasasComponent } from './mis-casas.component';

describe('MisCasasComponent', () => {
  let component: MisCasasComponent;
  let fixture: ComponentFixture<MisCasasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MisCasasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MisCasasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
