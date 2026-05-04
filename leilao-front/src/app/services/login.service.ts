import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable, computed, signal } from '@angular/core';
import { LoginResponse } from '../types/login-response.type';
import { catchError, tap, throwError } from 'rxjs';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  apiUrl: string = "http://localhost:8080/auth/"
  readonly token = signal<string | null>(sessionStorage.getItem('auth-token'));
  readonly isAuthenticated = computed(() => !!this.token());

  constructor(private httpClient: HttpClient) {}

  readonly userRoles = computed(() => {
    const token = this.token();
    if (!token) return [];
    try {
      const decoded: any = jwtDecode(token);
      console.log("Roles: " + decoded.roles);
      return decoded.roles || []; // O backend deve enviar as roles no payload
    } catch {
      return [];
    }
  });

  login(email: string, password: string) {
      return this.httpClient.post<LoginResponse>(this.apiUrl+"login", {email, password}).pipe(
        tap((value) => {
          sessionStorage.setItem("auth-token",value.token)
          sessionStorage.setItem("username",value.nome)
        })
      )
  }

  logout() {
    sessionStorage.removeItem('auth-token');
    this.token.set(null);
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
