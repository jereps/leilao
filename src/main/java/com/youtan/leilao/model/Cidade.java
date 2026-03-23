package com.youtan.leilao.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cidade")
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 200, nullable = false)
    private String nome;

//    @ManyToOne
//    @JoinColumn(name = "estado_id")
//    private Estado estado;

}
