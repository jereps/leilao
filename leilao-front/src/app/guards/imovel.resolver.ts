import { ResolveFn } from '@angular/router';
import { inject } from '@angular/core';
import { of } from 'rxjs';
import { ImovelSubmit } from '../model/imovel-submit';
import { ImovelService } from '../services/imovel.service';

export const imovelResolver: ResolveFn<ImovelSubmit> = (route, state) => {
  const imovelService = inject(ImovelService);
  const id = route.paramMap.get('idI');

  if (id){
    return imovelService.loadById(Number(id));
  }

return of({
  nome: '',
  tipoImovel: '',
  preco: '',
  metragem: '',
  nQuartos: '',
  nBanheiros: '',
  tipo: 'IMOVEL',
});
};

