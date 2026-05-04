import { Injectable } from '@angular/core';
import { Leilao } from '../model/leilao';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { first, tap } from 'rxjs';
import { LeilaoSubmit } from '../model/leilao-submit';
import { Itens } from '../model/itens';

@Injectable({
  providedIn: 'root',
})
export class LeilaoService {
  private readonly API = 'http://localhost:8080/leilao';

  constructor(private httpClient: HttpClient) {}

  lista() {
    return this.httpClient.get<Leilao[]>(this.API).pipe(
      first(),
      tap((l) => console.log(l)),
    );
  }

    listaItens() {
    return this.httpClient.get<Itens[]>(this.API).pipe(
      first(),
      tap((l) => console.log(l)),
    );
  }

  loadById(id: number) {
    return this.httpClient.get<LeilaoSubmit>(`${this.API}/${id}`);
  }

    loadByIds(id: number) {
    return this.httpClient.get<Leilao[]>(`${this.API}/itens/${id}`);
  }

  save(record: Partial<LeilaoSubmit>) {
    if(record.id){
      console.log('update');
      return this.update(record);
    }
    console.log('create');
    return this.create(record);
  }

  private create(record: Partial<LeilaoSubmit>) {
    console.log('LeilaoService');
    console.log(record);
    return this.httpClient.post<LeilaoSubmit>(this.API, record).pipe(first());
  }

  private update(record: Partial<LeilaoSubmit>){
    return this.httpClient.put<LeilaoSubmit>(`${this.API}/${record.id}`,record).pipe(first());
  }

  remove(id: number){
    return this.httpClient.delete<LeilaoSubmit>(`${this.API}/${id}`).pipe(first());
  }
}
