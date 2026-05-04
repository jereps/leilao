import { appConfig } from '../../app.config';
import { ChangeDetectionStrategy, Component, effect, inject, input } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
// import { MatFormFieldModule } from '@angular/material/form-field';
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
import { ActivatedRoute } from '@angular/router';
import { LeilaoSubmit } from '../../model/leilao-submit';
// import { NgxMaskDirective } from 'ngx-mask';

@Component({
  selector: 'app-show-form',
  imports: [
    ReactiveFormsModule,
    // MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    MatSelectModule,
    MatButtonModule,
    MatSnackBarModule,
    MatTimepickerModule,
    MatDatepickerModule,
    // NgxMaskDirective,

  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './leilao-show.component.html',
  styleUrl: './leilao-show.component.scss',
  providers: [provideNativeDateAdapter()],
})
export class LeilaoShowComponent {
  private route = inject(ActivatedRoute);
  private location = inject(Location);
  private snackBar = inject(MatSnackBar);
  private service = inject(LeilaoService);
  private formBuilder = inject(FormBuilder);


  // form: FormGroup;

    leilao = input<LeilaoSubmit>();

    // const leilao: LeilaoSubmit = this.route.snapshot.data['leilao'];

    private dataFiller = effect(() => {
      const data = this.leilao();
      if (data) {
        console.log(data);
        this.form.patchValue(data);
      }
    });

    form = this.formBuilder.nonNullable.group({
      id: new Number(),
      nome: '',
      dataHorarioLeilao: new Date(),
      categoria: '',
      descricao: '',
      enderecoLeilaoDTO: this.formBuilder.group({
        id: new Number(),
        numero: '',
        rua: '',
        cep: this.formBuilder.group({
          id: new Number(),
          cep: '',
        }),
        bairro: this.formBuilder.group({
          id: new Number(),
          nomeBairro: '',
        }),
        cidade: this.formBuilder.group({
          id: new Number(),
          nome: '',
        }),
        estado: this.formBuilder.group({
          id: new Number(),
          nome: '',
          sigla: '',
        }),
      }),
      financeiraDTO: this.formBuilder.group({
        id: new Number(),
        cnpj: 0,
        codigoCompensacao: 0,
        razaoSocial: '',
      }),

    });

    constructor(){
      this.form.disable();
    }

  onSubmit() {
    this.location.back();
  }
  onCancel() {
    this.location.back();
  }

  private onSucsess(men: string) {
    this.snackBar.open(`Leião  ${men} com sucesso!`, 'Done', { duration: 5000 });
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
