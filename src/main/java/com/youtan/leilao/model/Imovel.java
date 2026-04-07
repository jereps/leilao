package com.youtan.leilao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "imoveis")
public class Imovel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private TipoImovel tipoImovel;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#,##0.00",locale = "pt-BR")
    @Column(precision = 25, scale = 2)
    private BigDecimal preco;

    @Column(length = 200, nullable = false)
    private String nome;

    @ManyToOne()
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    private Integer metragem;
    private Integer nQuartos;
    private Integer nBanheiros;

}
