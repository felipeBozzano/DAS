import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FederacionesComponent } from './federaciones.component';

describe('FederacionesComponent', () => {
  let component: FederacionesComponent;
  let fixture: ComponentFixture<FederacionesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FederacionesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FederacionesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
