import { Itens } from '../../model/itens';
import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { toSignal } from '@angular/core/rxjs-interop';
import { LeilaoService } from '../../services/leilao.service';
import { catchError, of } from 'rxjs';

@Component({
  selector: 'app-imovel-list',
  imports: [
    MatTableModule,
    MatIconModule,
    MatCardModule,
    MatButtonModule,
  ],
  templateUrl: './imovel-detalhe.component.html',
  styleUrl: './imovel-detalhe.component.scss',
})
export class ImovelDetalheComponent {
private route = inject(ActivatedRoute);
  private leilaoService = inject(LeilaoService);

   id = this.route.snapshot.paramMap.get('id');

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
    this.leilaoService.loadByIds(Number(this.id)).pipe(
      catchError((error) => {
        console.log("itens-list");
        console.log(error);
        return of([]);
      }),
    ),
    { initialValue: [] },
  );

    leiloes = toSignal(
    this.leilaoService.lista().pipe(
      catchError((error) => {
        console.log(error);
        return of([]);
      }),
    ),
    { initialValue: [] },
  );


  onAdd() {
    // this.add.emit(true);
    // this.leilao()?.dataHorarioLeilao
    console.log("add");
  }
  onEdit(leilao: Itens){
    // this.edit.emit(leilao);
    console.log(leilao.id);
  }

  onDelete(leilao: Itens) {
    // this.remove.emit(leilao);
    console.log(leilao.id);
  }

  clickedRows(leilao: Itens) {
    // this.iten.emit(leilao);
    console.log(leilao.id);
  }
}
