import { Injectable } from '@angular/core';
import { Leilao } from '../model/leilao';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { first, tap } from 'rxjs';
import { LeilaoSubmit } from '../model/leilao-submit';

@Injectable({
  providedIn: 'root',
})
export class LeilaoService {

  private readonly API = 'http://localhost:8080/leilao'

  constructor(private httpClient: HttpClient) { }

  private headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer {{sessionStorage.getItem("auth-token")}}'
  });


  lista() {
    return this.httpClient.get<Leilao[]>(this.API)
    .pipe(
      first(),
      tap(l => console.log(l))
    );
  }

  save(record: LeilaoSubmit) {
  console.log("LeilaoService");
    console.log(record);
    return this.httpClient.post<LeilaoSubmit>(this.API,record).pipe(first());
  }

}
