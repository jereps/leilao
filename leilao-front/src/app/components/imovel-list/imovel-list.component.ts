import { Component, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { toSignal } from '@angular/core/rxjs-interop';
import { catchError, of } from 'rxjs';
import { ImovelService } from '../../services/imovel.service';
import { ImovelSubmit } from '../../model/imovel-submit';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LeilaoShowComponent } from "../leilao-show/leilao-show.component";
import { LoginService } from '../../services/login.service';

@Component({
  selector: 'app-imovel-list',
  imports: [
    MatTableModule,
    MatIconModule,
    MatCardModule,
    MatButtonModule,
    LeilaoShowComponent
],
  templateUrl: './imovel-list.component.html',
  styleUrl: './imovel-list.component.scss',
})
export class ImovelListComponent {
private route = inject(ActivatedRoute);
  private imovelService = inject(ImovelService);
  private router = inject(Router);
  private snackBar = inject(MatSnackBar);

  id = this.route.snapshot.paramMap.get('id');
  loginService = inject(LoginService);

  constructor(){}
    readonly displayedColumns: string[] = [
      'nome',
      'metragem',
      'nBanheiros',
      'nQuartos',
      'tipoImovel',
      'preco',
      'actions',
    ];


  itens = toSignal(
    this.imovelService.loadByIds(Number(this.id)).pipe(
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

  onEdit(imovel: ImovelSubmit){
    console.log(imovel.id);
    this.router.navigate(['edit',imovel.id], { relativeTo: this.route });
  }

  onDelete(imovel: ImovelSubmit) {
    this.imovelService.remove(Number(imovel.id)).subscribe(
      (result) => {
        this.snackBar.open('Imovel removido com sucesso!', 'X', {
          duration: 5000,
          verticalPosition: 'top',
          horizontalPosition: 'center',
        });
        this.refresh()
      },
      (error) => this.onError(error),
    );
    console.log(imovel.id);
  }

  clickedRows(imovel: ImovelSubmit) {
    this.router.navigate(['show', imovel.id],  { state: { imovel }, relativeTo: this.route }).catch((error) =>{
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
