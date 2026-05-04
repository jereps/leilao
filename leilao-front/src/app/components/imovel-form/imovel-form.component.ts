import { ChangeDetectionStrategy, Component, effect, inject, input } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { Location } from '@angular/common';
import { MatTimepickerModule } from '@angular/material/timepicker';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { ActivatedRoute } from '@angular/router';
import { NgxMaskDirective } from 'ngx-mask';
import { ImovelService } from '../../services/imovel.service';
import { ImovelSubmit } from '../../model/imovel-submit';

@Component({
  selector: 'app-imovel-form',
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
    NgxMaskDirective,

  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './imovel-form.component.html',
  styleUrl: './imovel-form.component.scss',
  providers: [provideNativeDateAdapter()],
})
export class ImovelFormComponent {
  private route = inject(ActivatedRoute);
  private location = inject(Location);
  private snackBar = inject(MatSnackBar);
  private service = inject(ImovelService);
  private formBuilder = inject(FormBuilder);

  id = this.route.snapshot.paramMap.get('id');

    imovel = input<ImovelSubmit>();


    private dataFiller = effect(() => {
      const data = this.imovel();
      if (data) {
        console.log(data);
        this.form.patchValue(data);
      }
    });

    form = this.formBuilder.nonNullable.group({
      id: undefined,
      nome: '',
      tipoImovel: '',
      preco: '',
      endereco: this.formBuilder.group({
        id: undefined,
        numero: '',
        rua: '',
        cep: this.formBuilder.group({
          id: undefined,
          cep: '',
        }),
        bairro: this.formBuilder.group({
          id: undefined,
          nomeBairro: '',
        }),
        cidade: this.formBuilder.group({
          id: undefined,
          nome: '',
        }),
        estado: this.formBuilder.group({
          id: undefined,
          nome: '',
          sigla: '',
        }),
      }),
      metragem: '',
      nQuartos: '',
      nBanheiros: '',
      tipo: 'IMOVEL'

    });

  onSubmit() {
    console.log("id"+this.id)
    this.service.save(this.form.getRawValue() as ImovelSubmit,Number(this.id)).subscribe(
      (result) =>{
        let men: string = 'Criado';
        if(this.form.getRawValue().id){
          men = 'Atualizado';
        };
        this.onSucsess(men)
      },
      (error) => this.onError(error),
    );
  }

  onCancel() {
    this.location.back();
  }

  private onSucsess(men: string) {
    this.snackBar.open(`Imovel  ${men} com sucesso!`, 'Done', { duration: 5000 });
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
