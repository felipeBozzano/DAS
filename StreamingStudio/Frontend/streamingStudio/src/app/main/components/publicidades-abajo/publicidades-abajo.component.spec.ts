import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PublicidadAbajoComponent } from './publicidades-abajo.component';

describe('PublicidadesAbajoComponent', () => {
  let component: PublicidadAbajoComponent;
  let fixture: ComponentFixture<PublicidadAbajoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PublicidadAbajoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PublicidadAbajoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
