import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginResponse } from '../types/login-response.type';
import { catchError, tap, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  apiUrl: string = "http://localhost:8080/auth/"

  constructor(private httpClient: HttpClient) {}

  login(email: string, password: string) {
      return this.httpClient.post<LoginResponse>(this.apiUrl+"login", {email, password}).pipe(
        tap((value) => {
          sessionStorage.setItem("auth-token",value.token)
          sessionStorage.setItem("username",value.nome)
        })
      )
  }

    signup(nome: string, email: string, password: string, role: string) {
      return this.httpClient.post<LoginResponse>(this.apiUrl+"register", {nome,email, password,role}).pipe(
        tap((value) => {
          sessionStorage.setItem("auth-token",value.token)
          sessionStorage.setItem("username",value.nome)
          sessionStorage.setItem("email",value.email)
        })
      ).pipe(
        catchError((error: HttpErrorResponse) => {
      console.error('Erro capturado:', error.error);
      // Retorna uma mensagem amigável ou o próprio erro
      return throwError(() => new Error(error.message));
    }))
  }
}
