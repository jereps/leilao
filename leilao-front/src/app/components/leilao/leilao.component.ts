import { Component, effect, inject } from '@angular/core';
import { LeilaoService } from '../../services/leilao.service';
import { catchError, of } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { LeilaoListComponent } from '../leilao-list/leilao-list.component';
import { MatCardModule } from '@angular/material/card';
import { LeilaoSubmit } from '../../model/leilao-submit';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-leilao',
  imports: [LeilaoListComponent, MatIconModule, MatCardModule, MatButtonModule],
  templateUrl: './leilao.component.html',
  styleUrl: './leilao.component.scss',
})
export class LeilaoComponent {
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private leilaoService = inject(LeilaoService);
  private snackBar = inject(MatSnackBar);



  leiloes = toSignal(
    this.leilaoService.lista().pipe(
      catchError((error) => {
        console.log(error);
        return of([]);
      }),
    ),
    { initialValue: [] },
  );

  refresh(){
    window.location.reload();
  }



  onAdd() {
    this.router.navigate(['new'], { relativeTo: this.route });
  }

  onEdit(leilao: LeilaoSubmit) {
    this.router.navigate(['edit', leilao.id], { relativeTo: this.route });
  }

  onRemove(leilao: LeilaoSubmit) {
    this.leilaoService.remove(leilao.id as number).subscribe(
      (result) => {
        this.snackBar.open('Leião removido com sucesso!', 'X', {
          duration: 5000,
          verticalPosition: 'top',
          horizontalPosition: 'center',
        });
        this.refresh()
      },
      (error) => this.onError(error),
    );

  }

  private onError(error: { error: { mensagem: any; erros: any } }) {
    this.snackBar.open(
      ` ${error.error.mensagem}
          ${error.error.erros}`,
      'Done',
      { duration: 50000 },
    );
  }


  onIten(leilao: LeilaoSubmit) {
    if(leilao.categoria=='VEICULO'){
      // console.log("VEICULO");
      this.router.navigate(['veiculos', leilao.id],  { state: { leilao }, relativeTo: this.route });
    } else{
    this.router.navigate(['imoveis', leilao.id],  { state: { leilao }, relativeTo: this.route });
    }
  }
}
