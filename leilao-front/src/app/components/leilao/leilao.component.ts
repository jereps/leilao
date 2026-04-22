import { Component } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { Leilao } from '../../model/leilao';
import { MatCardModule } from '@angular/material/card';
import { LeilaoService } from '../../services/leilao.service';
import { catchError, Observable, of } from 'rxjs';
import { DatePipe } from '@angular/common'; // 1. Importe o pipe
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { ActivatedRoute, Router, provideRouter } from '@angular/router';

@Component({
  selector: 'app-leilao',
  imports: [
    MatTableModule,
    MatCardModule,
    DatePipe,
    MatIconModule,
    MatButtonModule,
  ],
  templateUrl: './leilao.component.html',
  styleUrl: './leilao.component.scss',
})
export class LeilaoComponent {
  leiloes: Observable<Leilao[]>;
  displayedColumns: string[] = [
    'id',
    'nome',
    'DataHorario',
    'categoria',
    'descricao',
    'actions',
  ];

  constructor(
    private leilaoService: LeilaoService,
    private router: Router,
    private route: ActivatedRoute,
  ) {
    this.leiloes = this.leilaoService.lista().pipe(
      catchError((error) => {
        console.log(error);
        return of([]);
      }),
    );
  }

  onAdd() {
    this.router.navigate(['new'], { relativeTo: this.route });
  }
}
