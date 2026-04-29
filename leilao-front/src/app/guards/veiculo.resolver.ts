import { ResolveFn } from '@angular/router';
import { inject } from '@angular/core';
import { of } from 'rxjs';
import { VeiculoSubmit } from '../model/veiculo-submit';
import { VeiculoService } from '../services/veiculo.service';

export const veiculoResolver: ResolveFn<VeiculoSubmit> = (route, state) => {
  const veiculoService = inject(VeiculoService);
  const id = route.paramMap.get('idV');

  if (id){
    return veiculoService.loadById(Number(id));
  }

return of({
    placa: '',
    marcaModelo: '',
    anoFabricacao: '',
    Cor: '',
    tipoCombustivel: '',
    tipoVeiculo: '',
    nPortas: '',
    qtdPassageiros: '',
    valor: '',
    tipo: 'VEICULO',
});
};

