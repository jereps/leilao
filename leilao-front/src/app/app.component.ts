import { Component, inject } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { LoginService } from './services/login.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,MatToolbarModule,MatButtonModule,MatIconModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {

  title = 'leilao-front';
  private router = inject(Router);
  authService = inject(LoginService);

  onLogin() {
    this.router.navigate(['login']);
    console.log("logado? " + this.authService.isAuthenticated())
  }

  home() {
    this.router.navigate(['']);
  }

  onLogout() {
    this.authService.logout();
    this.router.navigate(['']);
    console.log("logado? " + this.authService.isAuthenticated())
  }

  onRefresh(){
    window.location.reload();
  }
}
