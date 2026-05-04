import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeilaoShowComponent } from './leilao-show.component';

describe('LeilaoShowComponent', () => {
  let component: LeilaoShowComponent;
  let fixture: ComponentFixture<LeilaoShowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LeilaoShowComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeilaoShowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
