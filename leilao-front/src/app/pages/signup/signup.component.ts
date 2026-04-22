
import { LoginService } from '../../services/login.service';
import { Component } from '@angular/core';
import { DefaultLoginLayoutComponent } from '../../components/default-login-layout/default-login-layout.component';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PrimaryInputComponent } from '../../components/primary-input/primary-input.component';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

interface SignupForm {
  name: FormControl,
  email: FormControl,
  password: FormControl,
  passwordConfirm: FormControl,
  role: FormControl
}

@Component({
    selector: 'app-signup',
    imports: [
        DefaultLoginLayoutComponent,
        ReactiveFormsModule,
        PrimaryInputComponent
    ],
    providers: [
        LoginService,
        ToastrService
    ],
    templateUrl: './signup.component.html',
    styleUrl: './signup.component.scss'
})
export class SignupComponent {
  signupForm!: FormGroup<SignupForm>;

  constructor(
    private router: Router,
    private loginService: LoginService,
    private toastrService: ToastrService
  ){
    this.signupForm = new FormGroup({
      name: new FormControl('',[Validators.required,Validators.minLength(3)]),
      email: new FormControl('', [Validators.required,Validators.email]),
      role: new FormControl('',),
      password: new FormControl('',[Validators.required,Validators.minLength(4)]),
      passwordConfirm: new FormControl('',[Validators.required,Validators.minLength(4)])
    })
  }

  submit(){
    this.loginService.signup(this.signupForm.value.name,this.signupForm.value.email,
      this.signupForm.value.password,this.signupForm.value.role
    ).subscribe({
      next: () => this.toastrService.success("Signup feito com sucesso!"+sessionStorage.getItem("email")),
      error: () => this.toastrService.error("Erro inesperado! Tente novamente")
    })
  }

  navigate(){
    this.router.navigate(["login"])
  }
}
