import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImovelShowComponent } from './imovel-show.component';

describe('ImovelShowComponent', () => {
  let component: ImovelShowComponent;
  let fixture: ComponentFixture<ImovelShowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ImovelShowComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ImovelShowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
