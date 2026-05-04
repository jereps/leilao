import { Itens } from '../../model/itens';
import { Component, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { toSignal } from '@angular/core/rxjs-interop';
import { LeilaoService } from '../../services/leilao.service';
import { catchError, of } from 'rxjs';
import { JsonPipe } from '@angular/common';
import { MatSnackBar } from '@angular/material/snack-bar';
import { VeiculoSubmit } from '../../model/veiculo-submit';
import { VeiculoService } from '../../services/veiculo.service';
import { LeilaoShowComponent } from "../leilao-show/leilao-show.component";
import { LoginService } from '../../services/login.service';

@Component({
  selector: 'app-veiculo-list',
  imports: [
    MatTableModule,
    MatIconModule,
    MatCardModule,
    MatButtonModule,
    LeilaoShowComponent
],
  templateUrl: './veiculo-list.component.html',
  styleUrl: './veiculo-list.component.scss',
})
export class VeiculoListComponent {
// private router = Inject(Router);
private route = inject(ActivatedRoute);
  private veiculoService = inject(VeiculoService);
  private router = inject(Router);
  private snackBar = inject(MatSnackBar);
  loginService = inject(LoginService);

  id = this.route.snapshot.paramMap.get('id');


  constructor(){}
    readonly displayedColumns: string[] = [
      'cor',
      'anoFabricacao',
      'marcaModelo',
      'placa',
      'tipoCombustivel',
      'tipoVeiculo',
      'valor',
      'actions',
    ];




  itens = toSignal(
    this.veiculoService.loadByIds(Number(this.id)).pipe(
      catchError((error) => {
        console.log("itens-list");
        console.log(error);
        return of([]);
      }),
    ),
    { initialValue: [] },
  );

  onAdd() {
    this.router.navigate(['new'], { relativeTo: this.route });
    console.log("add");
  }

  onEdit(veiculo: VeiculoSubmit){
    console.log(veiculo.id);
    this.router.navigate(['edit',veiculo.id], { relativeTo: this.route });
  }

  onDelete(veiculo: VeiculoSubmit) {
    this.veiculoService.remove(Number(veiculo.id)).subscribe(
      (result) => {
        this.snackBar.open('Veiculo removido com sucesso!', 'X', {
          duration: 5000,
          verticalPosition: 'top',
          horizontalPosition: 'center',
        });
        this.refresh()
      },
      (error) => this.onError(error),
    );
    console.log(veiculo.id);
  }

  clickedRows(veiculo: VeiculoSubmit) {
    this.router.navigate(['show', veiculo.id],  { state: { veiculo }, relativeTo: this.route }).catch((error) =>{
       this.snackBar.open(
      `Precisa estar Logado.`,
      'Done',
      { duration: 50000 },
    );
  });
  }

  refresh(){
    window.location.reload();
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
