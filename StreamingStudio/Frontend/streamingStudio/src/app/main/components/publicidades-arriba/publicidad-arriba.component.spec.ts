import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PublicidadArribaComponent } from './publicidad-arriba.component';

describe('PublicidadComponent', () => {
  let component: PublicidadArribaComponent;
  let fixture: ComponentFixture<PublicidadArribaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PublicidadArribaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PublicidadArribaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
