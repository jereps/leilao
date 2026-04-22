import { Component } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { Leilao } from '../../model/leilao';
import {MatCardModule} from '@angular/material/card';
import { LeilaoService } from '../../services/leilao.service';
import { catchError, Observable, of } from 'rxjs';
import { DatePipe } from '@angular/common'; // 1. Importe o pipe


@Component({
  selector: 'app-leilao',
  imports: [
    MatTableModule,
    MatCardModule,
    DatePipe
  ],
  templateUrl: './leilao.component.html',
  styleUrl: './leilao.component.scss',
})
export class LeilaoComponent {



  leiloes: Observable<Leilao[]>;
  displayedColumns: string[] = ['nome','DataHorario','categoria','descricao'];

    constructor(private leilaoService: LeilaoService) {
    this.leiloes = this.leilaoService.lista()
    .pipe(
      catchError(error => {
        console.log(error);
        return of([])
      })

    );
   }

}
