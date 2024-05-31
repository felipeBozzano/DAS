import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsuarioFederacionComponent } from './usuario-federacion.component';

describe('UsuarioFederacionComponent', () => {
  let component: UsuarioFederacionComponent;
  let fixture: ComponentFixture<UsuarioFederacionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UsuarioFederacionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UsuarioFederacionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
