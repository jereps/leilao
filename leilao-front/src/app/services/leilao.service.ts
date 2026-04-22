import { Injectable } from '@angular/core';
import { Leilao } from '../model/leilao';
import { HttpClient } from '@angular/common/http';
import { first, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LeilaoService {

  private readonly API = 'http://localhost:8080/leilao'

  constructor(private httpClient: HttpClient) { }

  lista() {
    return this.httpClient.get<Leilao[]>(this.API)
    .pipe(
      first(),
      tap(l => console.log(l))
    );
  }

}
