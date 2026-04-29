import { Injectable } from '@angular/core';
import { Leilao } from '../model/leilao';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { first, tap } from 'rxjs';
import { LeilaoSubmit } from '../model/leilao-submit';
import { Itens } from '../model/itens';
import { ImovelSubmit } from '../model/imovel-submit';

@Injectable({
  providedIn: 'root',
})
export class ImovelService {
  private readonly API = 'http://localhost:8080/leilao';
  private readonly APIIM = 'http://localhost:8080/api/imovel';

  constructor(private httpClient: HttpClient) {}

  loadById(id: number) {
    return this.httpClient.get<ImovelSubmit>(`${this.APIIM}/${id}`).pipe(
      first(),
      tap((l) => console.log(l)),
    );
  }

    loadByIds(id: number) {
    return this.httpClient.get<Leilao[]>(`${this.API}/itens/${id}`);
  }

  save(record: Partial<ImovelSubmit>,id: number) {

          console.log('submit + Imovel');
          console.log(record);
    if(record.id){
      console.log('update');
      return this.update(record);
    }
    console.log('create');
    return this.create(record,id);
  }

  private create(record: Partial<ImovelSubmit>,id: number) {
    console.log('ImoveloService');
    console.log(record);
    return this.httpClient.post<ImovelSubmit>(`${this.API}/${id}/${record.tipo}`, record).pipe(first());
  }

  private update(record: Partial<ImovelSubmit>){
    return this.httpClient.put<ImovelSubmit>(`${this.APIIM}/${record.id}`,record).pipe(first());
  }

  remove(id: number){
    return this.httpClient.delete<ImovelSubmit>(`${this.APIIM}/${id}`).pipe(first());
  }
}
