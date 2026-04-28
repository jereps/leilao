import { LeilaoSubmit } from './../model/leilao-submit';
import { LeilaoService } from './../services/leilao.service';
import { ResolveFn } from '@angular/router';
import { inject } from '@angular/core';
import { of } from 'rxjs';

export const leilaoResolver: ResolveFn<LeilaoSubmit> = (route, state) => {
  const leilaoService = inject(LeilaoService);
  const id = route.paramMap.get('id');

  if (id){
    return leilaoService.loadById(Number(id));
  }

  return of({
  nome: '',
  dataHorarioLeilao: new Date(),
  descricao: '',
  categoria: '',
});
};

