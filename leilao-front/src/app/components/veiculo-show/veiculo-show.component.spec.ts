import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VeiculoShowComponent } from './veiculo-show.component';

describe('VeiculoShowComponent', () => {
  let component: VeiculoShowComponent;
  let fixture: ComponentFixture<VeiculoShowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VeiculoShowComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VeiculoShowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
