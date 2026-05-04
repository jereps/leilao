import { Leilao } from './../../model/leilao';
import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { DatePipe } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { LeilaoSubmit } from '../../model/leilao-submit';
import { LoginService } from '../../services/login.service';

@Component({
  selector: 'app-leilao-list',
  imports: [
    MatTableModule,
    DatePipe,
    MatIconModule,
    MatCardModule,
    MatButtonModule
  ],
  templateUrl: './leilao-list.component.html',
  styleUrl: './leilao-list.component.scss',
})
export class LeilaoListComponent {
loginService = inject(LoginService);


  constructor(
  ){}
    readonly displayedColumns: string[] = [
      'nome',
      'DataHorario',
      'categoria',
      'descricao',
      'actions',
    ];

  @Input() leiloes: Leilao[] = [];
  @Output() add = new EventEmitter(false);
  @Output() edit = new EventEmitter(false);
  @Output() remove = new EventEmitter(false);
  @Output() iten = new EventEmitter(false);

  onAdd() {
    this.add.emit(true);
  }
  onEdit(leilao: LeilaoSubmit){
    this.edit.emit(leilao);
  }

  onDelete(leilao: LeilaoSubmit) {
    this.remove.emit(leilao);
  }

  clickedRows(leilao: LeilaoSubmit) {
    this.iten.emit(leilao);
  }
}
