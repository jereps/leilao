import { TestBed } from '@angular/core/testing';
import { ResolveFn } from '@angular/router';

import { leilaoResolver } from './leilao.resolver';

describe('leilaoResolver', () => {
  const executeResolver: ResolveFn<boolean> = (...resolverParameters) => 
      TestBed.runInInjectionContext(() => leilaoResolver(...resolverParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeResolver).toBeTruthy();
  });
});
