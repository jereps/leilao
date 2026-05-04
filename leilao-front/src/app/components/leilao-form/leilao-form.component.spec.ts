import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeilaoFormComponent } from './leilao-form.component';

describe('LeilaoFormComponent', () => {
  let component: LeilaoFormComponent;
  let fixture: ComponentFixture<LeilaoFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LeilaoFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeilaoFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
