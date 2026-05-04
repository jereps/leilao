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
import { VeiculoService } from '../../services/veiculo.service';
import { VeiculoSubmit } from '../../model/veiculo-submit';

@Component({
  selector: 'app-show-form',
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
  templateUrl: './veiculo-show.component.html',
  styleUrl: './veiculo-show.component.scss',
  providers: [provideNativeDateAdapter()],
})
export class VeiculoShowComponent {
  private route = inject(ActivatedRoute);
  private location = inject(Location);
  private snackBar = inject(MatSnackBar);
  private service = inject(VeiculoService);
  private formBuilder = inject(FormBuilder);

  id = this.route.snapshot.paramMap.get('id');

    veiculo = input<VeiculoSubmit>();


    private dataFiller = effect(() => {
      const data = this.veiculo();
      if (data) {
        console.log(data);
        this.form.patchValue(data);
      }
    });

    form = this.formBuilder.nonNullable.group({
        id: undefined,
        placa: '',
        marcaModelo: '',
        anoFabricacao: '',
        Cor: '',
        tipoCombustivel: '',
        tipoVeiculo: '',
        valor: '',
        nPortas: '0',
        qtdPassageiros: '2',
        tipo: 'VEICULO',

    });

  constructor(){
      this.form.disable();
  }

  onSubmit() {
    this.onCancel();
  }

  onCancel() {
    this.location.back();
  }

  private onSucsess(men: string) {
    this.snackBar.open(`Veiculo  ${men} com sucesso!`, 'Done', { duration: 5000 });
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
