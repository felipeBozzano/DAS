import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PublicidadDerechaComponent } from './publicidad-derecha.component';

describe('PublicidadComponent', () => {
  let component: PublicidadDerechaComponent;
  let fixture: ComponentFixture<PublicidadDerechaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PublicidadDerechaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PublicidadDerechaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
