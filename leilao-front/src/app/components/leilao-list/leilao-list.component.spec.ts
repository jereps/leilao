import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeilaoListComponent } from './leilao-list.component';

describe('LeilaoListComponent', () => {
  let component: LeilaoListComponent;
  let fixture: ComponentFixture<LeilaoListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LeilaoListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeilaoListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
