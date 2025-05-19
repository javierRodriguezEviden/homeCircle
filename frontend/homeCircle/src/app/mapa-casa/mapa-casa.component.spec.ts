import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MapaCasaComponent } from './mapa-casa.component';

describe('MapaCasaComponent', () => {
  let component: MapaCasaComponent;
  let fixture: ComponentFixture<MapaCasaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MapaCasaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MapaCasaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
