import { Injectable } from '@angular/core';
import { Leilao } from '../model/leilao';
import { HttpClient } from '@angular/common/http';
import { first, tap } from 'rxjs';
import { VeiculoSubmit } from '../model/veiculo-submit';

@Injectable({
  providedIn: 'root',
})
export class VeiculoService {
  private readonly API = 'http://localhost:8080/leilao';
  private readonly APIV = 'http://localhost:8080/api/veiculo';

  constructor(private httpClient: HttpClient) {}

  loadById(id: number) {
    return this.httpClient.get<VeiculoSubmit>(`${this.APIV}/${id}`).pipe(
      first(),
      tap((l) => console.log(l)),
    );
  }

    loadByIds(id: number) {
    return this.httpClient.get<Leilao[]>(`${this.API}/itens/${id}`);
  }

  save(record: Partial<VeiculoSubmit>,id: number) {

    console.log('submit + Veiculo');
    console.log(record);
    if(record.id){
      console.log('update');
      return this.update(record);
    }
    console.log('create');
    return this.create(record,id);
  }

  private create(record: Partial<VeiculoSubmit>,id: number) {
    console.log('VeiculoService');
    console.log(record);
    return this.httpClient.post<VeiculoSubmit>(`${this.API}/${id}/${record.tipo}`, record).pipe(first());
  }

  private update(record: Partial<VeiculoSubmit>){
    return this.httpClient.put<VeiculoSubmit>(`${this.APIV}/${record.id}`,record).pipe(first());
  }

  remove(id: number){
    return this.httpClient.delete<VeiculoSubmit>(`${this.APIV}/${id}`).pipe(first());
  }
}
