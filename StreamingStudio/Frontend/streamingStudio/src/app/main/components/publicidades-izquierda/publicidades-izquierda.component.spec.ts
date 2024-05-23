import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PublicidadIzquierdaComponent } from './publicidades-izquierda.component';

describe('PublicidadesAbajoComponent', () => {
  let component: PublicidadIzquierdaComponent;
  let fixture: ComponentFixture<PublicidadIzquierdaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PublicidadIzquierdaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PublicidadIzquierdaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
