package com.youtan.leilao.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "financeira")
public class Financeira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 14, nullable = false)
    private Long cnpj;
    @Column(length = 3, nullable = false)
    private Long codigoCompensacao;
    @Column(length = 200, nullable = false)
    private String razaoSocial;
}
