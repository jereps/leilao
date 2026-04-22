import { ChangeDetectionStrategy, Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { LeilaoService } from '../../services/leilao.service';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { Location } from '@angular/common';
import { MatTimepickerModule } from '@angular/material/timepicker';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';

@Component({
  selector: 'app-leilao-form',
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    MatSelectModule,
    MatButtonModule,
    MatSnackBarModule,
    MatTimepickerModule,
    MatDatepickerModule,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './leilao-form.component.html',
  styleUrl: './leilao-form.component.scss',
  providers: [provideNativeDateAdapter()],
})
export class LeilaoFormComponent {
  form: FormGroup;
  select = '';

  constructor(
    private formBuilder: FormBuilder,
    private service: LeilaoService,
    private snackBar: MatSnackBar,
    private location: Location,
  ) {
    this.form = this.formBuilder.group({
      nome: [],
      dataHorarioLeilao: [],
      enderecoLeilaoDTO: this.formBuilder.group({
        numero: [],
        rua: [],
        cep: this.formBuilder.group({
          cep: [],
        }),
        bairro: this.formBuilder.group({
          nomeBairro: [],
        }),
        cidade: this.formBuilder.group({
          nome: [],
        }),
        estado: this.formBuilder.group({
          nome: [],
          sigla: [],
        }),
      }),
      categoria: [],
      descricao: [],
    });
  }

  onSubmit() {
    this.service.save(this.form.value).subscribe(
      (result) => this.onSucsess(),
      (error) => this.onError(error),
    );
  }
  onCancel() {
    this.location.back();
  }

  private onSucsess() {
    this.snackBar.open('Leião Criado com sucesso!', 'Done', { duration: 5000 });
    this.onCancel();
  }

  private onError(error: { error: { mensagem: any; erros: any } }) {
    this.snackBar.open(
      ` ${error.error.mensagem}
          ${error.error.erros}`,
      'Done',
      { duration: 50000 },
    );
  }
}
